package com.wherecar.rest.service;

import com.wherecar.rest.domain.GeoInfo;
import com.wherecar.rest.dto.GeoFenceResponse;
import com.wherecar.rest.dto.GeoInfoRegistRequest;

public interface GeoInfoService {

    void createGeoInfo(GeoInfoRegistRequest geoInfoRegistRequest);
    // todo: emulator에 GeoFence정보 전송
    GeoFenceResponse getGeoInfo (Long id);
    void updateGeoInfo(Long id, GeoInfoRegistRequest GeoInfoRegistRequest);
    void deleteGeoInfo(Long id);

}
