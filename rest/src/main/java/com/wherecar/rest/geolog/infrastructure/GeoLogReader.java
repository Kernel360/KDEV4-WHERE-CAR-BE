package com.wherecar.rest.geolog.infrastructure;

import com.wherecar.rest.geolog.domain.GeoLog;

import java.util.List;

public interface GeoLogReader {
    List<GeoLog> getGeoLogByMdn(String mdn);
    GeoLog getGeoLogById(Long geoLogId);
}
