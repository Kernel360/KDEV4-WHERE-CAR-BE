package com.wherecar.collector.car.infrastructure;

import com.wherecar.collector.car.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByMdn(String mdn);
}
