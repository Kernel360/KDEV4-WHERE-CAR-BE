package com.wherecar.rest.carlog.infrastructure;

import com.wherecar.rest.carlog.domain.CarLog;
import com.wherecar.rest.carlog.infrastructure.querydsl.CarLogSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CarLogRepository extends JpaRepository<CarLog, Long>, CarLogSearch {

    @Query("SELECT cl FROM CarLog cl JOIN Car c ON cl.mdn = c.mdn WHERE c.company.id = :userCompanyId")
    Page<CarLog> findByCompanyId(@Param("userCompanyId") Long userCompanyId, Pageable pageable);

    List<CarLog> findByMdnIn(List<String> mdns);

}
