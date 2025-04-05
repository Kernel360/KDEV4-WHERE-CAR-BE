package com.wherecar.collector.service;

import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.CarLog;
import com.wherecar.collector.domain.CarStatus;
import com.wherecar.collector.domain.GpsConditionType;
import com.wherecar.collector.dto.CarLogRequest;
import com.wherecar.collector.dto.CarLogResponse;
import com.wherecar.collector.dto.ResponseCode;
import com.wherecar.collector.repository.CarLogRepository;
import com.wherecar.collector.repository.CarRepository;
import com.wherecar.collector.repository.CarStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional  // readOnly X
public class CarLogConverterServiceImpl implements CarLogConverterService {

    private final CarLogSaveService carLogSaveService;

    private final CarLogRepository carLogRepository;
    private final CarRepository carRepository;
    private final CarStatusRepository carStatusRepository;

    /*
     TODO
     시동 ON 데이터 전송 시, 만일 GPS 미수신 상태라면 하기로 설정하여 전송한다.
         위경도 값은 직전 시동 OFF의 위경도 값으로 설정한다.
         GPS 상태는 ‘P’값으로 설정한 후 전송한다.
         설치 후, 최초 시동 ON의 경우 그 전에 저장된 GPS 데이터가 없기 때문에 위경도 없이 보낸다. (상태값은 V, GPS 장치 인식 안된 경우는 0)
     시동 ON 정보 전달을 요청할 경우, 요청 전문 헤더 부분의 Token 값이 필수로 설정한다.
     시동 ON 시 GPS 상태가 정상적이지 않으면, GPS 상태 값(gcd)을 ‘P’로 설정하고, 직전 시동 OFF 때의 GPS 위치 정보를 보낸다.
    */
    @Override
    public CarLogResponse receiveOnLog(CarLogRequest onLogRequest) {

        Car car = carRepository.findByMdn(onLogRequest.getMdn()).orElseThrow(() -> new RuntimeException("존재하지 않는 차입니다."));

        // 직전 시동 OFF일 때의 CarLog를 찾는 쿼리 메서드 호출
        Optional<CarLog> optionalPreviousCarLog = carLogRepository.findTopByMdnOrderByOffTimeDesc(car.getMdn());

        // 차가 최초 출고가 아닌 상황
        if (optionalPreviousCarLog.isPresent()) {
            CarLog previousCarLog = optionalPreviousCarLog.get();

            // 시동 ON 시 최초 누적 거리는 그 직전 시동 OFF일 때의 누적 거리 값과 일치해야 한다.
            if (Objects.equals(previousCarLog.getOffSum(), Integer.parseInt(onLogRequest.getSum()))) {

                // 시동 ON 시 mileage는 직전 시동 OFF 시 mileage
                Integer onMileage = previousCarLog.getOffMileage();

                // 위도, 경도 Double로 변환
                Double doubleLatitude = (double) Integer.parseInt(onLogRequest.getLat()) / 1000000;
                Double doubleLongitude = (double) Integer.parseInt(onLogRequest.getLon()) / 1000000;

                // DateTimeFormatter를 사용하여 형식 지정
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

                // 문자열을 LocalDateTime으로 변환
                LocalDateTime onTime = LocalDateTime.parse(onLogRequest.getOnTime(), formatter);

                GpsConditionType onGpsCondition;

                try {
                    onGpsCondition = GpsConditionType.valueOf(onLogRequest.getGcd());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("잘못된 값입니다. 유효한 값은 A, V, O입니다.");
                }

                // onLogRequest -> CarLog로 변환
                CarLog carLog = CarLog.builder()
                        .mdn(onLogRequest.getMdn())
                        .onGpsCondition(onGpsCondition)
                        .onLatitude(doubleLatitude)
                        .onLongitude(doubleLongitude)
                        .onAngle(Integer.parseInt(onLogRequest.getAng()))
                        .onSpeed(Integer.parseInt(onLogRequest.getSpd()))
                        .onSum(Integer.parseInt(onLogRequest.getSum()))
                        .onMileage(onMileage)
                        .onTime(onTime)
                        .build();

                // TODO 이렇게 하는 게 맞는지 확인하기(mileage 저장)
                CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
                carStatus.changeMileage(onMileage);

                // 로그와 차 상태를 저장하는 서비스 호출
                carLogSaveService.saveCarLog(carLog, carStatus);

                // TODO 일단 무조건 성공한다고 가정하고 작성. 그 외의 경우도 생각해 보기
                return CarLogResponse.builder()
                        .rstCd(ResponseCode.SUCCESS.getCode())
                        .rstMsg(ResponseCode.SUCCESS.getMessage())
                        .mdn(onLogRequest.getMdn())
                        .build();
            }

            if (!Objects.equals(previousCarLog.getOffSum(), Integer.parseInt(onLogRequest.getSum()))) {
                // TODO (시동 ON 시 최초 누적 거리) != (직전 시동 OFF일 때의 누적 거리)일 때 어떻게 처리할지 생각해 보기
                throw new RuntimeException("(시동 ON 시 최초 누적 거리) != (직전 시동 OFF일 때의 누적 거리)");
            }
        }

        // 차는 Repository에 저장되어 있지만 최초 출고인 상황
        if (optionalPreviousCarLog.isEmpty()) {

            // 최초 출고일 땐 mileage가 0
            Integer onMileage = 0;

            // 위도, 경도 Double로 변환
            Double doubleLatitude = (double) Integer.parseInt(onLogRequest.getLat()) / 1000000;
            Double doubleLongitude = (double) Integer.parseInt(onLogRequest.getLon()) / 1000000;

            // DateTimeFormatter를 사용하여 형식 지정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            // 문자열을 LocalDateTime으로 변환
            LocalDateTime onTime = LocalDateTime.parse(onLogRequest.getOnTime(), formatter);

            GpsConditionType onGpsCondition;

            try {
                onGpsCondition = GpsConditionType.valueOf(onLogRequest.getGcd());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("잘못된 값입니다. 유효한 값은 A, V, O입니다.");
            }

            // onLogRequest -> CarLog로 변환
            CarLog carLog = CarLog.builder()
                    .mdn(onLogRequest.getMdn())
                    .onGpsCondition(onGpsCondition)
                    .onLatitude(doubleLatitude)
                    .onLongitude(doubleLongitude)
                    .onAngle(Integer.parseInt(onLogRequest.getAng()))
                    .onSpeed(Integer.parseInt(onLogRequest.getSpd()))
                    .onSum(0)
                    .onMileage(onMileage)
                    .onTime(onTime)
                    .build();

            // TODO 이렇게 하는 게 맞는지 확인하기(mileage 저장)
            CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
            carStatus.changeMileage(onMileage);

            // 로그와 차 상태를 저장하는 서비스 호출
            carLogSaveService.saveCarLog(carLog, carStatus);

            // TODO 일단 무조건 성공한다고 가정하고 작성. 그 외의 경우도 생각해 보기
            return CarLogResponse.builder()
                    .rstCd(ResponseCode.SUCCESS.getCode())
                    .rstMsg(ResponseCode.SUCCESS.getMessage())
                    .mdn(onLogRequest.getMdn())
                    .build();
        }

        throw new RuntimeException("에러가 발생했습니다.");
    }

    /*
    TODO
    시동 OFF 데이터는 차량이 Key-Off 시점에만 전송한다. 만약 전송이 되지 않거나 실패할 경우 다음 시동 ON 데이터를 전송 할 때, 시동 OFF 데이터를 같이 보낸다. (시동 OFF가 먼저 전송되어야 함)
    시동 OFF 데이터는 최대 24시간의 데이터를 저장하며, 통신이 정상화 된 경우, 시동 OFF 데이터를 전송한다.
    시동 OFF 데이터 전송 시, 만일 GPS 미수신 상태라면 하기로 설정하여 전송한다.
        위경도 값은 가장 최근에 잡힌 GPS 위경값으로 설정한다.
        GPS 상태는 ‘P’값으로 설정한 후 전송한다.
    누적거리 계산 시, 초간 이동 거리가 80m 이상이면 해당 구간은 스킵하여 계산한다.
    데이터 전달의 순서는 시동ON – 시동OFF – 시동ON – 시동OFF 순으로 전송되어야 한다.
    시동 OFF 정보 전달을 요청할 경우, 요청 전문 헤더 부분의 Token 값이 필수로 설정한다.
    시동 OFF 시 GPS 상태가 정상적이지 않으면, GPS 상태 값(gcd)을 ‘P’로 설정한다.
    */
    @Override
    public CarLogResponse receiveOffLog(CarLogRequest offLogRequest) {

        Car car = carRepository.findByMdn(offLogRequest.getMdn()).orElseThrow(() -> new RuntimeException("존재하지 않는 차입니다."));

        // // 직전 시동 ON일 때의 CarLog를 찾는 쿼리 메서드 호출
        CarLog previousCarLog = carLogRepository.findTopByMdnOrderByOnTimeDesc(car.getMdn()).orElseThrow(() -> new RuntimeException("이전 ON 로그가 없습니다."));

        Integer onSum = previousCarLog.getOnSum();    // 직전 ON 로그의 sum
        Integer offSum = Integer.parseInt(offLogRequest.getSum());      // OFF 로그의 sum
        Integer sumToAdd = 0;

        if (onSum <= offSum) {
            sumToAdd = offSum - onSum;
        }
        if (onSum > offSum) {   // 주행 거리가 10,000km(10,000,000m)를 넘었을 경우
            sumToAdd = (offSum + 10000000) - onSum;
        }

        // TODO sumToAdd / 1000에서의 잘리는 데이터 어떻게 할지, 그냥 둘지 고민하기
        Integer offMileage = previousCarLog.getOnMileage() + sumToAdd / 1000;

        // 위도, 경도 Double로 변환
        Double doubleLatitude = (double) Integer.parseInt(offLogRequest.getLat()) / 1000000;
        Double doubleLongitude = (double) Integer.parseInt(offLogRequest.getLon()) / 1000000;

        // DateTimeFormatter를 사용하여 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // 문자열을 LocalDateTime으로 변환
        LocalDateTime onTime = LocalDateTime.parse(offLogRequest.getOnTime(), formatter);
        LocalDateTime offTime = LocalDateTime.parse(offLogRequest.getOffTime(), formatter);

        GpsConditionType offGpsCondition;

        try {
            offGpsCondition = GpsConditionType.valueOf(offLogRequest.getGcd());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("잘못된 값입니다. 유효한 값은 A, V, O입니다.");
        }

        // offLogRequest -> CarLog로 변환
        CarLog carLog = CarLog.builder()
                .mdn(offLogRequest.getMdn())
                .onGpsCondition(previousCarLog.getOnGpsCondition())
                .onLatitude(previousCarLog.getOnLatitude())
                .onLongitude(previousCarLog.getOnLongitude())
                .onAngle(previousCarLog.getOnAngle())
                .onSpeed(previousCarLog.getOnSpeed())
                .onSum(onSum)
                .onMileage(previousCarLog.getOnMileage())
                .onTime(onTime)
                .offGpsCondition(offGpsCondition)
                .offLatitude(doubleLatitude)
                .offLongitude(doubleLongitude)
                .offAngle(Integer.parseInt(offLogRequest.getAng()))
                .offSpeed(Integer.parseInt(offLogRequest.getSpd()))
                .offSum(Integer.parseInt(offLogRequest.getSum()))
                .offMileage(offMileage)
                .offTime(offTime)
                .build();

        // TODO 이렇게 하는 게 맞는지 확인하기(mileage 저장)
        CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
        carStatus.changeMileage(offMileage);

        // 로그와 차 상태를 저장하는 서비스 호출
        carLogSaveService.saveCarLog(carLog, carStatus);

        // TODO 일단 무조건 성공한다고 가정하고 작성. 그 외의 경우도 생각해 보기
        return CarLogResponse.builder()
                .rstCd(ResponseCode.SUCCESS.getCode())
                .rstMsg(ResponseCode.SUCCESS.getMessage())
                .mdn(offLogRequest.getMdn())
                .build();
    }
}
