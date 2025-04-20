package com.wherecar.collector.gpslog.infrastructure;

import com.wherecar.collector.gpslog.domain.GpsLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpsLogRepository extends JpaRepository<GpsLog, Long> {
}
