package com.wherecar.rest.repository;

import com.wherecar.rest.domain.CarLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarLogRepository extends JpaRepository<CarLog, Long> {
    Page<CarLog> findByCarId(Long carId, PageRequest pageRequest);
}
