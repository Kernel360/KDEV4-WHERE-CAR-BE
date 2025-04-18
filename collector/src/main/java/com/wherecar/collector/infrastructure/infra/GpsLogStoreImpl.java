package com.wherecar.collector.infrastructure.infra;

import com.wherecar.collector.application.dto.GpsLogInfo;
import com.wherecar.collector.application.dto.GpsLogRequest;
import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.CarStatus;
import com.wherecar.collector.domain.GpsLog;
import com.wherecar.collector.domain.GpsLogFactory;
import com.wherecar.collector.infrastructure.CarStatusRepository;
import com.wherecar.collector.infrastructure.GpsLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class GpsLogStoreImpl implements GpsLogStore {

    private final GpsLogFactory gpsLogFactory;
    private final GpsLogRepository gpsLogRepository;
    private final CarStatusRepository carStatusRepository;

    @Override
    public void storeGpsLogs(GpsLogRequest gpsLogRequest, Car car) {

        DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // 0 ~ 59초의 주기 정보 데이터 처리
        for (GpsLogInfo gpsLogInfo : gpsLogRequest.getCList()) {

            GpsLog gpsLog = gpsLogFactory.toGpsLog(gpsLogRequest, gpsLogInfo, timestampFormatter);
            gpsLogRepository.save(gpsLog);

            CarStatus carStatus = carStatusRepository.findByCarId(car.getId()).orElseThrow(() -> new RuntimeException("CarStatus가 없습니다."));
            carStatus.changeBatteryVoltage(Integer.parseInt(gpsLogInfo.getBat()));
            carStatusRepository.save(carStatus);
        }
    }
}
