package com.wherecar.batch.main.infrastructure;

import com.wherecar.batch.main.domain.GpsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface GpsLogRepository extends JpaRepository<GpsLog, Long> {
    Page<GpsLog> findByMdnAndTimestampBetween(String mdn, LocalDateTime start, LocalDateTime end, Pageable pageable);

}
