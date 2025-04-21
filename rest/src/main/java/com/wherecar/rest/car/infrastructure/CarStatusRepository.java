package com.wherecar.rest.car.infrastructure;

import com.wherecar.rest.car.domain.constant.CarState;
import com.wherecar.rest.car.domain.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarStatusRepository extends JpaRepository<CarStatus, Long> {

    @Query("SELECT COUNT(cs) FROM CarStatus cs WHERE cs.car.company.id = :companyId AND cs.carState = :carState")
    Long countByCompanyIdAndCarState(@Param("companyId") Long companyId, @Param("carState") CarState carState);

}
