package com.wherecar.rest.repository;

import com.wherecar.rest.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
