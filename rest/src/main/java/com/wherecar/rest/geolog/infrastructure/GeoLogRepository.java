package com.wherecar.rest.geolog.infrastructure;

import com.wherecar.rest.geolog.domain.GeoLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GeoLogRepository extends JpaRepository<GeoLog, Long>{

    @EntityGraph(attributePaths = {"geoInfo"})
    List<GeoLog> findByMdn(String mdn);

}