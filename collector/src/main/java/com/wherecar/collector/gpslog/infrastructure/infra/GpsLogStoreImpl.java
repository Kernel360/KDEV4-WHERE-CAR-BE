package com.wherecar.collector.gpslog.infrastructure.infra;

import com.wherecar.collector.car.domain.Car;
import com.wherecar.collector.car.domain.CarStatus;
import com.wherecar.collector.gpslog.domain.GpsLog;
import com.wherecar.collector.car.infrastructure.CarStatusRepository;
import com.wherecar.collector.gpslog.infrastructure.GpsLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GpsLogStoreImpl implements GpsLogStore {

    private final GpsLogRepository gpsLogRepository;
    private final CarStatusRepository carStatusRepository;

    @Override
    public void storeGpsLogs(List<GpsLog> gpsLogList, Car car, String bat) {

        boolean isFirst = true;

        for (GpsLog gpsLog : gpsLogList) {

            gpsLogRepository.save(gpsLog);

            if (isFirst) {
                CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
                carStatus.changeBatteryVoltage(Integer.parseInt(bat));
                carStatusRepository.save(carStatus);

                isFirst = false;
            }

        }

    }

}
