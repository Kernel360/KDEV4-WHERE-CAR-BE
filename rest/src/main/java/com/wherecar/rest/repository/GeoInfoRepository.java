package com.wherecar.rest.repository;

import com.wherecar.rest.domain.GeoInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GeoInfoRepository extends JpaRepository<GeoInfo, Long> {
    // todo: emulator에 geofence 정보 전송
}
