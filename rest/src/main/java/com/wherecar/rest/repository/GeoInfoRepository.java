package com.wherecar.rest.repository;

import com.wherecar.rest.domain.GeoInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface GeoInfoRepository extends JpaRepository<GeoInfo, Long> {

    @Query("SELECT gi FROM GeoInfo gi WHERE gi.company.id = :companyId")
    Page<GeoInfo> findByCompanyId(@Param("CompanyId") Long companyId, Pageable pageable);

    // todo: emulator에 geofence 정보 전송

}
