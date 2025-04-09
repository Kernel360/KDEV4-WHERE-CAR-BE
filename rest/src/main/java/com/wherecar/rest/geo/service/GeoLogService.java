package com.wherecar.rest.geo.service;

import com.wherecar.rest.geo.dto.GeoLogRequest;
import com.wherecar.rest.geo.dto.GeoLogResponse;

import java.util.List;

public interface GeoLogService {
    List<GeoLogResponse> getGeoLogByCarId(Long carId);
    GeoLogResponse getGeoLog(Long id);
    void updateGeoLog(Long id, GeoLogRequest geoLogRequest);
    void deleteGeoLog(Long id);
}
