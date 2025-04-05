package com.wherecar.rest.service;

import com.wherecar.rest.dto.GeoFenceResponse;
import com.wherecar.rest.dto.GeoFenceRequest;

import java.util.List;

public interface GeoInfoService {

    void createGeoInfo(GeoFenceRequest geoFenceRequest, Long companyId);

    List<GeoFenceResponse> getGeoFences(Long companyId, Integer page, Integer size);

    GeoFenceResponse getGeoInfo (Long id);

    void updateGeoInfo(Long id, GeoFenceRequest GeoFenceRequest);

    void deleteGeoInfo(Long id);

    // todo: emulator에 GeoFence정보 전송
}
