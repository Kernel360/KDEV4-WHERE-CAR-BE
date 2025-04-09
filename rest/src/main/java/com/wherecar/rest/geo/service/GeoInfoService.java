package com.wherecar.rest.geo.service;

import com.wherecar.rest.geo.dto.GeoFenceResponse;
import com.wherecar.rest.geo.dto.GeoInfoRegistRequest;

public interface GeoInfoService {

    void createGeoInfo(GeoInfoRegistRequest geoInfoRegistRequest);
    // todo: emulator에 GeoFence정보 전송
    GeoFenceResponse getGeoInfo (Long id);
    void updateGeoInfo(Long id, GeoInfoRegistRequest GeoInfoRegistRequest);
    void deleteGeoInfo(Long id);

}
