package com.wherecar.rest.geolog.infrastructure;

import com.wherecar.rest.geolog.domain.GeoLog;

public interface GeoLogStore {

    GeoLog store(GeoLog geoLog);
    void delete(Long geoLogId);

}