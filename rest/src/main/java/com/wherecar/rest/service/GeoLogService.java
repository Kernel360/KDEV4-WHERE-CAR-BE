package com.wherecar.rest.service;

import com.wherecar.rest.dto.GeoFenceLogRequest;
import com.wherecar.rest.dto.GeoFenceLogResponse;

public interface GeoLogService {
    GeoFenceLogResponse getGeoLog(Long id);
    void updateGeoLog(Long id, GeoFenceLogRequest geoFenceLogRequest);
    void deleteGeoLog(Long id);
}
