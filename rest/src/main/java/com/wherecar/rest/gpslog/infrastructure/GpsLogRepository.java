package com.wherecar.rest.gpslog.infrastructure;

import com.wherecar.rest.gpslog.domain.GpsLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GpsLogRepository extends JpaRepository<GpsLog, Long> {

    Optional<GpsLog> findTopByMdnOrderByTimestampDesc(String mdn);

    List<GpsLog> findByMdnAndTimestampBetweenOrderByTimestamp(String mdn, LocalDateTime startTime, LocalDateTime endTime);

}
