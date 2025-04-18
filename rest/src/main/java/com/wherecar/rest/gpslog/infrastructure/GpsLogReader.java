package com.wherecar.rest.gpslog.infrastructure;

import com.wherecar.rest.gpslog.application.dto.GpsPoint;
import com.wherecar.rest.gpslog.domain.GpsLog;

import java.time.LocalDateTime;
import java.util.List;

public interface GpsLogReader {
    GpsLog findTopByMdnOrderByTimestampDesc(String mdn);
    List<GpsPoint> findByMdnAndTimestampBetweenOrderByTimestamp(String mdn, LocalDateTime startTime, LocalDateTime endTime);
}
