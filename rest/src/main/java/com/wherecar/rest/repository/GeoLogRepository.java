package com.wherecar.rest.repository;

import com.wherecar.rest.domain.GeoLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GeoLogRepository extends JpaRepository<GeoLog, Long>{

    List<GeoLog> getGeoLogByMdn(String mdn);
    @EntityGraph(attributePaths = {"geoInfo"})
    Optional<GeoLog> findById(Long id);

}