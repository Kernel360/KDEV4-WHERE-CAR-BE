package com.wherecar.collector.gpslog.infrastructure;

import com.wherecar.collector.car.domain.Car;
import com.wherecar.collector.gpslog.domain.GpsLog;
import com.wherecar.collector.car.infrastructure.CarStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GpsLogStoreImpl implements GpsLogStore {

    private final GpsLogRepository gpsLogRepository;
    private final CarStatusRepository carStatusRepository;

    @Override
    @Transactional
    public void store(List<GpsLog> gpsLogList, Car car, List<String> batList) {

        gpsLogRepository.saveAll(gpsLogList);

        String lastBattery = batList.get(batList.size() - 1);
        carStatusRepository.updateBatteryVoltage(car.getId(), Integer.parseInt(lastBattery));
    }

}
