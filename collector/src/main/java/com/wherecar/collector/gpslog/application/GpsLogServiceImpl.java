package com.wherecar.collector.gpslog.application;

import com.wherecar.collector.car.domain.Car;
import com.wherecar.collector.gpslog.application.dto.GpsLogInfo;
import com.wherecar.collector.gpslog.application.dto.GpsLogRequest;
import com.wherecar.collector.gpslog.domain.GpsLog;
import com.wherecar.collector.gpslog.domain.GpsLogFactory;
import com.wherecar.collector.car.infrastructure.CarReader;
import com.wherecar.collector.gpslog.infrastructure.GpsLogStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GpsLogServiceImpl implements GpsLogService {

    private final CarReader carReader;
    private final GpsLogStore gpsLogStore;
    private final GpsLogFactory gpsLogFactory;

    @Override
    @Async
    public void receiveGpsLogs(GpsLogRequest gpsLogRequest) {
        try {
            Car car = carReader.getCarByMdn(gpsLogRequest.getMdn());
            List<String> batList = gpsLogRequest.getCList().stream()
                    .map(GpsLogInfo::getBat)
                    .toList();
            List<GpsLog> gpsLogList = gpsLogFactory.toGpsLogList(gpsLogRequest);
            gpsLogStore.storeGpsLogs(gpsLogList, car, batList);
        } catch (Exception e) {
            log.error("GPS 로그 저장 비동기 처리 예외 발생", e);
        }
    }
}


//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class GpsLogServiceImpl implements GpsLogService {
//
//    private final CarReader carReader;
//    private final GpsLogStore gpsLogStore;
//    private final GpsLogFactory gpsLogFactory;
//
//    @Override
//    public void receiveGpsLogs(GpsLogRequest gpsLogRequest) {
//
//        Car car = carReader.getCarByMdn(gpsLogRequest.getMdn());
//        List<String> batList = gpsLogRequest.getCList().stream()
//                .map(GpsLogInfo::getBat)
//                .toList();
//        List<GpsLog> gpsLogList = gpsLogFactory.toGpsLogList(gpsLogRequest);
//        gpsLogStore.storeGpsLogs(gpsLogList, car, batList);
//    }
//}
