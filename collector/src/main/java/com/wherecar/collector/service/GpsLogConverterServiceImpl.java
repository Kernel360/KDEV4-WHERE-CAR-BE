package com.wherecar.collector.service;

import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.CarStatus;
import com.wherecar.collector.domain.GpsLog;
import com.wherecar.collector.dto.GpsLogInfo;
import com.wherecar.collector.dto.GpsLogRequest;
import com.wherecar.collector.dto.GpsLogResponse;
import com.wherecar.collector.dto.ResponseCode;
import com.wherecar.collector.repository.CarRepository;
import com.wherecar.collector.repository.CarStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional  // readOnly X
public class GpsLogConverterServiceImpl implements GpsLogConverterService {

    private final GpsLogSaveService gpsLogSaveService;

    private final CarRepository carRepository;
    private final CarStatusRepository carStatusRepository;

    /*
      TODO
      GPS 상태와 관계없이 0 ~ 59초의 총 60개의 데이터를 전송한다. (GPS 값을 얻지 못한 경우 ‘0’으로 설정하여 데이터를 구성한다.)
      GPS 상태가 감도가 좋을 경우 ‘A’, GPS 위치 값을 신뢰할 수 없을 경우 ‘V’, GPS 장치가 인식이 안되는 경우 ‘0’으로 설정한다.
     예기치 않은 상황으로 인해 주기 데이터를 전송하지 못하는 경우 최대 1시간의 데이터를 저장하며 주기 데이터를 전송할 수 있는 상황이 될 경우 저장된 데이터를 먼저 전송한 후 그 후에 쌓인 데이터를 전송한다.
     최초 시동 ON 후 거리 계산 시, 시동 OFF 시 저장된 GPS 마지막 위치를 기준으로 계산한다. (GPS 수신 지연 시간을 고려하여 거리 계산의 정확도 향상을 위함)
     누적거리 계산 시, 초간 이동 거리가 80m 이이면 해당 구간은 스킵하여 계산한다.
     주기 정보 전달을 요청할 경우, 요청 전문 헤더 부분의 Token 값이 필수로 설정한다.
     */
    @Override
    public GpsLogResponse receiveGpsLog(GpsLogRequest gpsLogRequest) {
        Car car = carRepository.findByMdn(gpsLogRequest.getMdn()).orElseThrow(() -> new RuntimeException("존재하지 않는 차입니다."));

        List<GpsLogInfo> cList = gpsLogRequest.getCList();

        DateTimeFormatter oTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String oTimeString = gpsLogRequest.getOTime().format(oTimeFormatter);   // oTime을 String(yyyyMMddHHmm 형식)으로 변환

        DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // 0 ~ 59초의 주기 정보 데이터 처리
        for (GpsLogInfo gpsLogInfo : cList) {
            String sec = gpsLogInfo.getSec();

            String timestampString = oTimeString + sec;     // oTime(yyyyMMddHHmm 형식) + sec(ss 형식) 의 String
            LocalDateTime timestamp = LocalDateTime.parse(timestampString, timestampFormatter); // String을 LocalDateTime으로 변환

            // 위도, 경도 Double로 변환
            Double doubleLatitude = (double) gpsLogInfo.getLat() / 1000000;
            Double doubleLongitude = (double) gpsLogInfo.getLon() / 1000000;

            // GpsLogInfo -> GpsLog로 변환
            GpsLog gpsLog = GpsLog.builder()
                    .mdn(gpsLogRequest.getMdn())
                    .timestamp(timestamp)    // oTime + sec
                    .gpsCondition(gpsLogInfo.getGcd())
                    .latitude(doubleLatitude)
                    .longitude(doubleLongitude)
                    .angle(gpsLogInfo.getAng())
                    .speed(gpsLogInfo.getSpd())
                    .sum(gpsLogInfo.getSum())
                    .build();

            // 로그를 저장하는 서비스 호출
            gpsLogSaveService.saveGpsLog(gpsLog);

            // TODO 이렇게 하는 게 맞는지 확인하기(배터리 저장)
            CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
            carStatus.changeBatteryVoltage(gpsLogInfo.getBat());

            carStatusRepository.save(carStatus);    // 배터리 최신화 후 자동차 상태 저장
        }

        // TODO 일단 무조건 성공한다고 가정하고 작성. 그 외의 경우도 생각해 보기
        return GpsLogResponse.builder()
                .rstCd(ResponseCode.SUCCESS.getCode())
                .rstMsg(ResponseCode.SUCCESS.getMessage())
                .mdn(gpsLogRequest.getMdn())
                .build();
    }
}
