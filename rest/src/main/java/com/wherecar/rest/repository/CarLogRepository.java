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

    //Todo: company 연결 후 NULL 허용 제거
    @EntityGraph(attributePaths = {"car"})
    @Query("SELECT cl FROM CarLog cl WHERE cl.company.id = :userCompanyId OR cl.company IS NULL")
    Page<CarLog> findByCompanyId(@Param("userCompanyId") Long userCompanyId, Pageable pageable);

}
