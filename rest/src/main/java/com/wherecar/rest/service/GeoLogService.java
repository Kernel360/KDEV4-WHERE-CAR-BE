package com.wherecar.rest.service;

import com.wherecar.rest.dto.GeoLogRequest;
import com.wherecar.rest.dto.GeoLogResponse;

import java.util.List;

public interface GeoLogService {
    List<GeoLogResponse> getGeoLogByCarId(Long carId);
    GeoLogResponse getGeoLog(Long id);
    void updateGeoLog(Long id, GeoLogRequest geoLogRequest);
    void deleteGeoLog(Long id);
}
