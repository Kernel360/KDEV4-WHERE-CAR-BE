package com.wherecar.batch.main.infrastructure;

import com.wherecar.batch.main.domain.CarLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CarLogRepository extends JpaRepository<CarLog, Long> {
    Page<CarLog> findByOffTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

}
