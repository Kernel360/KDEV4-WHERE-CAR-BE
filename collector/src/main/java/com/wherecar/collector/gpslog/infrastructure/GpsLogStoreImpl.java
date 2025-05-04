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
    public void store(List<GpsLog> gpsLogList, Car car, List<String> batList) {

        for (int i = 0; i < gpsLogList.size(); i++) {
            GpsLog gpsLog = gpsLogList.get(i);
            String bat = batList.get(i);

            gpsLogRepository.save(gpsLog);

            if (i == gpsLogList.size() - 1) {
                carStatusRepository.updateBatteryVoltage(car.getId(), Integer.parseInt(bat));
            }
        }

    }

}
