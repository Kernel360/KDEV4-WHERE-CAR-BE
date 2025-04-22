package com.wherecar.batch.main.infrastructure;

import com.wherecar.batch.main.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByMdn(String mdn);
}
