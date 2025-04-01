package com.wherecar.rest.service;

import com.wherecar.rest.domain.GpsLog;
import com.wherecar.rest.dto.GpsLogResponse;
import com.wherecar.rest.repository.GpsLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GpsLogServiceImpl implements GpsLogService {

    private final GpsLogRepository gpsLogRepository;

    @Override
    public GpsLogResponse getLatestLocation(String mdn) {

        GpsLog gpsLog = gpsLogRepository.findTopByCar_MdnOrderByTimestampDesc(mdn)
                .orElseThrow(() -> new RuntimeException("해당 차량의 GPS 로그가 없습니다."));

        return GpsLogResponse.builder()
                .mdn(gpsLog.getCar().getMdn())
                .longitude(gpsLog.getLongitude())
                .latitude(gpsLog.getLatitude())
                .timestamp(gpsLog.getTimestamp())
                .build();

    }

}
