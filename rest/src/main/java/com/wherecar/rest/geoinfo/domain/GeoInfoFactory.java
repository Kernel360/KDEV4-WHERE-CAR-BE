package com.wherecar.rest.geoinfo.domain;

import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.company.presentation.CompanyController;
import com.wherecar.rest.geoinfo.application.dto.GeoInfoRequest;
import com.wherecar.rest.geoinfo.application.dto.GeoInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GeoInfoFactory {
    public GeoInfo toGeoInfo(GeoInfoRequest geoInfoRequest, Company company) {
        return GeoInfo.builder()
                .name(geoInfoRequest.getName())
                .geoEventType(geoInfoRequest.getGeoEventType())
                .geoRange(geoInfoRequest.getGeoRange())
                .latitude(geoInfoRequest.getLatitude())
                .longitude(geoInfoRequest.getLongitude())
                .onTime(geoInfoRequest.getOnTime())
                .offTime(geoInfoRequest.getOffTime())
                .company(company)
                .build();
    }
    public GeoInfoResponse toGeoInfoResponse(GeoInfo geoInfo) {
        return GeoInfoResponse.builder()
                .id(geoInfo.getId())
                .name(geoInfo.getName())
                .geoEventType(geoInfo.getGeoEventType())
                .geoRange(geoInfo.getGeoRange())
                .latitude(geoInfo.getLatitude())
                .longitude(geoInfo.getLongitude())
                .onTime(geoInfo.getOnTime())
                .offTime(geoInfo.getOffTime())
                .build();
    }

    public List<GeoInfoResponse> toGeoInfoResponses(List<GeoInfo> geoInfos) {
        return geoInfos.stream()
                .map(geoInfo -> GeoInfoResponse.builder()
                        .id(geoInfo.getId())
                        .name(geoInfo.getName())
                        .geoEventType(geoInfo.getGeoEventType())
                        .geoRange(geoInfo.getGeoRange())
                        .latitude(geoInfo.getLatitude())
                        .longitude(geoInfo.getLongitude())
                        .onTime(geoInfo.getOnTime())
                        .offTime(geoInfo.getOffTime())
                        .build())
                .collect(Collectors.toList());
    }
}
