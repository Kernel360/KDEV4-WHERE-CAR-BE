package com.wherecar.rest.repository;

import com.wherecar.rest.domain.Car;
import com.wherecar.rest.domain.CarLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarLogRepository extends JpaRepository<CarLog, Long> {

    @Query("SELECT cl FROM CarLog cl JOIN Car c ON cl.mdn = c.mdn WHERE c.company.id = :userCompanyId")
    Page<CarLog> findByCompanyId(@Param("userCompanyId") Long userCompanyId, Pageable pageable);

    @Query("SELECT cl FROM CarLog cl JOIN Car c ON cl.mdn = c.mdn WHERE c.company.id = :userCompanyId AND c.mdn = :mdn")
    Page<CarLog> findByCompanyIdAndCarId(@Param("userCompanyId") Long userCompanyId, @Param("mdn") String mdn, Pageable pageable);

}
