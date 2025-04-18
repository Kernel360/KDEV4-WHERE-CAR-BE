package com.wherecar.rest.geoinfo.application;

import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.geoinfo.domain.GeoInfo;
import com.wherecar.rest.geoinfo.application.dto.GeoInfoRequest;
import com.wherecar.rest.geoinfo.application.dto.GeoInfoResponse;
import com.wherecar.rest.geoinfo.infrastructure.GeoInfoRepository;
import com.wherecar.rest.company.infrastructure.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


public interface GeoInfoService {
    GeoInfoResponse createGeoInfo(Long companyId, GeoInfoRequest geoInfoRequest);
    GeoInfoResponse getGeoInfo(Long geoInfoId);
    GeoInfoResponse updateGeoInfo(Long geoInfoId, GeoInfoRequest geoInfoRequest);
    void deleteGeoInfo(Long geoInfoId);
    List<GeoInfoResponse> getGeoInfosByCompanyId(Long companyId);

}
