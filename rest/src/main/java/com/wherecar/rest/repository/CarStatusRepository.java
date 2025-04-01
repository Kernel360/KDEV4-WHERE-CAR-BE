package com.wherecar.rest.repository;

import com.wherecar.rest.domain.Car;
import com.wherecar.rest.domain.CarStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarStatusRepository extends JpaRepository<CarStatus, Long> {
}
