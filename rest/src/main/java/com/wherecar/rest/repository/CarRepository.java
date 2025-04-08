package com.wherecar.rest.repository;

import com.wherecar.rest.domain.Car;
import com.wherecar.rest.domain.CarLog;
import com.wherecar.rest.domain.OwnerType;
import jdk.jshell.Snippet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    //car detail (carStatus 포함)
    @Query("SELECT c FROM Car c JOIN FETCH c.carStatus WHERE c.id = :carId")
    Optional<Car> findCarWithStatus(@Param("carId") Long carId);


    //car list (carStatus 포함)
    //Todo: company 연결하고 NULL 허용 안되게 수정
    @EntityGraph(attributePaths = {"carStatus"})
    @Query("SELECT c FROM Car c WHERE c.company.id = :userCompanyId OR :userCompanyId IS NULL")
    Page<Car> findByCompanyIdWithCarStatus(@Param("userCompanyId") Long userCompanyId, Pageable pageable);

    Optional<Car> findByMdn(String mdn);

    long countByCompanyId(Long companyId);
    long countByCompanyIdAndOwnerType(Long companyId, OwnerType ownerType);

    // 대시보드 운행통계관련 해당회사 보유 차량조회
    @Query("SELECT c.mdn FROM Car c WHERE c.company.id = :companyId")
    List<String> findMdnsByCompanyId(@Param("companyId") Long companyId);
}
