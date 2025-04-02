package com.wherecar.rest.repository;

import com.wherecar.rest.domain.GpsLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GpsLogRepository extends JpaRepository<GpsLog, Long> {

    @EntityGraph(attributePaths = {"car"})
    Optional<GpsLog> findTopByCar_MdnOrderByTimestampDesc(String mdn);

    @EntityGraph(attributePaths = {"car"})
    List<GpsLog> findByCar_MdnAndTimestampBetweenOrderByTimestamp(String mdn, LocalDateTime startTime, LocalDateTime endTime);

}
