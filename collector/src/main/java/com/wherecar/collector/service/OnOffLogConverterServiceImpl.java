package com.wherecar.collector.service;

import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.OnOffLog;
import com.wherecar.collector.dto.OnOffLogRequest;
import com.wherecar.collector.repository.CarRepository;
import com.wherecar.collector.repository.OnOffLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional  // readOnly X
public class OnOffLogConverterServiceImpl implements OnOffLogConverterService {

    private final OnOffLogSaveService onOffLogSaveService;

    private final OnOffLogRepository onOffLogRepository;
    private final CarRepository carRepository;


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
    public void receiveOnLog(OnOffLogRequest onLogRequest) {

        Optional<Car> optionalCar = carRepository.findByMdn(onLogRequest.getMdn());

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();

            // 직전 시동 OFF일 때의 OnOffLog를 찾는 쿼리 메서드 호출
            Optional<OnOffLog> optionalPreviousOnOffLog = onOffLogRepository.findTopByCarIdOrderByOffTimeDesc(car.getId());

            if (optionalPreviousOnOffLog.isPresent()) {
                OnOffLog previousOnOffLog = optionalPreviousOnOffLog.get();

                // 시동 ON 시 최초 누적 거리는 그 직전 시동 OFF일 때의 누적 거리 값과 일치해야 한다.
                if (previousOnOffLog.getOffSum() == onLogRequest.getSum()) {

                    // 시동 ON 시 mileage는 직전 시동 OFF 시 mileage
                    Integer onMileage = previousOnOffLog.getOffMileage();

                    // onLogRequest -> OnOffLog로 변환
                    OnOffLog onOffLog = OnOffLog.builder()
                            .car(car)
                            .gpsCondition(onLogRequest.getGcd())
                            .latitude(onLogRequest.getLat())
                            .longitude(onLogRequest.getLon())
                            .angle(onLogRequest.getAng())
                            .speed(onLogRequest.getSpd())
                            .onSum(onLogRequest.getSum())
                            .onMileage(onMileage)
                            .onTime(onLogRequest.getOnTime())
                            .build();

                    // 로그를 저장하는 서비스 호출
                    onOffLogSaveService.saveOnOffLog(onOffLog);
                }
            }

            // 차는 Repository에 저장되어 있지만 최초 출고인 상황
            if (optionalPreviousOnOffLog.isEmpty()) {
                Integer onMileage = 0;

                // onLogRequest -> OnOffLog로 변환
                OnOffLog onOffLog = OnOffLog.builder()
                        .car(car)
                        .gpsCondition(onLogRequest.getGcd())
                        .latitude(onLogRequest.getLat())
                        .longitude(onLogRequest.getLon())
                        .angle(onLogRequest.getAng())
                        .speed(onLogRequest.getSpd())
                        .onSum(0)
                        .onMileage(onMileage)
                        .onTime(onLogRequest.getOnTime())
                        .build();

                // 로그를 저장하는 서비스 호출
                onOffLogSaveService.saveOnOffLog(onOffLog);
            }

        }

        if (optionalCar.isEmpty()) {
            // TODO 차가 저장되어 있지 않으면 예외 발생?
        }

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
    public void receiveOffLog(OnOffLogRequest offLogRequest) {

        Optional<Car> optionalCar = carRepository.findByMdn(offLogRequest.getMdn());

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();

            // // 직전 시동 ON일 때의 OnOffLog를 찾는 쿼리 메서드 호출
            Optional<OnOffLog> optionalPreviousOnOffLog = onOffLogRepository.findTopByCarIdOrderByOnTimeDesc(car.getId());

            if (optionalPreviousOnOffLog.isPresent()) {
                OnOffLog previousOnOffLog = optionalPreviousOnOffLog.get();

                Integer onSum = previousOnOffLog.getOnSum();    // 직전 ON 로그의 sum
                Integer offSum = offLogRequest.getSum();        // OFF 로그의 sum
                Integer sumToSave = 0;

                if (onSum <= offSum) {
                    sumToSave = offSum - onSum;
                }
                if (onSum > offSum) {   // 주행 거리가 10,000km(10,000,000m)를 넘었을 경우
                    sumToSave = (offSum + 10000000) - onSum;
                }

                // TODO sumToSave / 1000에서의 잘리는 데이터 어떻게 할지, 그냥 둘지 고민하기
                Integer offMileage = previousOnOffLog.getOnMileage() + sumToSave / 1000;

                // offLogRequest -> OnOffLog로 변환
                OnOffLog onOffLog = OnOffLog.builder()
                        .car(car)
                        .gpsCondition(offLogRequest.getGcd())
                        .latitude(offLogRequest.getLat())
                        .longitude(offLogRequest.getLon())
                        .angle(offLogRequest.getAng())
                        .speed(offLogRequest.getSpd())
                        .onSum(onSum)
                        .offSum(offLogRequest.getSum())
                        .onMileage(previousOnOffLog.getOnMileage())
                        .offMileage(offMileage)
                        .onTime(offLogRequest.getOnTime())
                        .offTime(offLogRequest.getOffTime())
                        .build();

                // 로그를 저장하는 서비스 호출
                onOffLogSaveService.saveOnOffLog(onOffLog);

            }

        }

        if (optionalCar.isEmpty()) {
            // TODO 차가 저장되어 있지 않으면 예외 발생?
        }

    }
}
