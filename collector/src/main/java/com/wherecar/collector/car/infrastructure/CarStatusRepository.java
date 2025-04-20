package com.wherecar.collector.car.infrastructure;

import com.wherecar.collector.car.domain.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarStatusRepository extends JpaRepository<CarStatus, Long> {
    Optional<CarStatus> findByCarId(Long carId);
}
