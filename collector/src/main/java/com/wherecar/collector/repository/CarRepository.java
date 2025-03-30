package com.wherecar.collector.repository;

import com.wherecar.collector.domain.Car;
import com.wherecar.collector.domain.OnOffLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByMdn(String mdn);
}
