package com.wherecar.rest.repository;

import com.wherecar.rest.domain.GeoLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeoLogRepository extends JpaRepository<GeoLog, Long>{

    @EntityGraph(attributePaths = {"geoInfo"})
    Optional<GeoLog> findById(Long id);

}