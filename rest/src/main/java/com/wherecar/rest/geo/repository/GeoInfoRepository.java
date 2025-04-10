package com.wherecar.rest.geo.repository;

import com.wherecar.rest.geo.domain.GeoInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GeoInfoRepository extends JpaRepository<GeoInfo, Long> {
    // todo: emulator에 geofence 정보 전송

    List<GeoInfo> findByCompanyId(Long companyId);
}
