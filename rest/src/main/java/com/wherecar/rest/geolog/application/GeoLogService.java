package com.wherecar.rest.geolog.application;

import com.wherecar.rest.geolog.application.dto.GeoLogRequest;
import com.wherecar.rest.geolog.application.dto.GeoLogResponse;

import java.util.List;

public interface GeoLogService {
    List<GeoLogResponse> getGeoLogByCarId(Long carId);
    GeoLogResponse getGeoLog(Long geoLogId);
    GeoLogResponse updateGeoLog(Long geoLogId, GeoLogRequest geoLogRequest);
    void deleteGeoLog(Long id);
}
