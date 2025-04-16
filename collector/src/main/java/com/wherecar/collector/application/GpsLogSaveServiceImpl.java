package com.wherecar.collector.application;

import com.wherecar.collector.domain.CarStatus;
import com.wherecar.collector.domain.GpsLog;
import com.wherecar.collector.infrastructure.CarStatusRepository;
import com.wherecar.collector.infrastructure.GpsLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GpsLogSaveServiceImpl implements GpsLogSaveService {

    private final GpsLogRepository gpsLogRepository;
    private final CarStatusRepository carStatusRepository;

    @Override
    public void saveGpsLog(GpsLog gpsLog, CarStatus carStatus) {
        gpsLogRepository.save(gpsLog);
        carStatusRepository.save(carStatus);    // 배터리 최신화 후 자동차 상태 저장
    }
}
