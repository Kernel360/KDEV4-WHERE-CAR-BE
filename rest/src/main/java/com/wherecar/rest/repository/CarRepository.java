package com.wherecar.rest.repository;

import com.wherecar.rest.domain.Car;
import jdk.jshell.Snippet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByCompanyId(Long userCompanyId, PageRequest pageRequest);
}
