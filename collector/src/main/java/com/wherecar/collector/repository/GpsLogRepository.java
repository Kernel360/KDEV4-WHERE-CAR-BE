package com.wherecar.collector.repository;

import com.wherecar.collector.domain.GpsLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpsLogRepository extends JpaRepository<GpsLog, Long> {
}
