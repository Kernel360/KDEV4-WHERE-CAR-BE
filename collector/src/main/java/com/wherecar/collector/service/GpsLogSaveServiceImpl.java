package com.wherecar.collector.service;

import com.wherecar.collector.domain.GpsLog;
import com.wherecar.collector.repository.GpsLogRepository;
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

    @Override
    public void saveGpsLog(GpsLog gpsLog) {
        gpsLogRepository.save(gpsLog);
    }
}
