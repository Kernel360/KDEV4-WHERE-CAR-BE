package com.wherecar.collector.service;

import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.GpsLog;
import com.wherecar.collector.dto.GpsLogInfo;
import com.wherecar.collector.dto.GpsLogRequest;
import com.wherecar.collector.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional  // readOnly X
public class GpsLogConverterServiceImpl implements GpsLogConverterService {

    private final GpsLogSaveService gpsLogSaveService;

    private final CarRepository carRepository;

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
    public void receiveGpsLog(GpsLogRequest gpsLogRequest) {
        Optional<Car> optionalCar = carRepository.findByMdn(gpsLogRequest.getMdn());

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();

            List<GpsLogInfo> cList = gpsLogRequest.getCList();
            System.out.println("확인: " + gpsLogRequest.getOTime());
            System.out.println("확인: " + gpsLogRequest.getCCnt());
            System.out.println("확인: " + cList.toString());
            DateTimeFormatter oTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            String oTimeString = gpsLogRequest.getOTime().format(oTimeFormatter);   // oTime을 String(yyyyMMddHHmm 형식)으로 변환

            DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            // 0 ~ 59초의 주기 정보 데이터 처리
            for (GpsLogInfo gpsLogInfo : cList) {
                String sec = gpsLogInfo.getSec();

                String timestampString = oTimeString + sec;     // oTime(yyyyMMddHHmm 형식) + sec(ss 형식) 의 String
                LocalDateTime timestamp = LocalDateTime.parse(timestampString, timestampFormatter); // String을 LocalDateTime으로 변환

                // GpsLogInfo -> GpsLog로 변환
                GpsLog gpsLog = GpsLog.builder()
                        .car(car)
                        .timestamp(timestamp)    // oTime + sec
                        .gpsCondition(gpsLogInfo.getGcd())
                        .latitude(gpsLogInfo.getLat())
                        .longitude(gpsLogInfo.getLon())
                        .angle(gpsLogInfo.getAng())
                        .speed(gpsLogInfo.getSpd())
                        .sum(gpsLogInfo.getSum())
                        .build();

                // 로그를 저장하는 서비스 호출
                gpsLogSaveService.saveGpsLog(gpsLog);

                // TODO 이렇게 하는 게 맞는지 확인하기(배터리 저장)
                car.changeBatteryVoltage(gpsLogInfo.getBat());
                carRepository.save(car);    // 배터리 최신화 후 자동차 저장
            }
        }

        if (optionalCar.isEmpty()) {
            throw new RuntimeException("존재하지 않는 차입니다.");
        }
    }
}
