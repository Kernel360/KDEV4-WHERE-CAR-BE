package com.wherecar.rest.repository;

import com.wherecar.rest.domain.Car;
import com.wherecar.rest.domain.CarState;
import com.wherecar.rest.domain.CarStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarStatusRepository extends JpaRepository<CarStatus, Long> {

    @Query("SELECT COUNT(cs) FROM CarStatus cs WHERE cs.car.company.id = :companyId AND cs.carState = :carState")
    long countByCompanyIdAndCarState(@Param("companyId") Long companyId, @Param("carState") CarState carState);

}
