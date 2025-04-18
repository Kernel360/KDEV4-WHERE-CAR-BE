package com.wherecar.rest.geoinfo.application;

import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.company.domain.CompanyFactory;
import com.wherecar.rest.company.infrastructure.CompanyReader;
import com.wherecar.rest.geoinfo.application.dto.GeoInfoRequest;
import com.wherecar.rest.geoinfo.application.dto.GeoInfoResponse;
import com.wherecar.rest.geoinfo.domain.GeoInfo;
import com.wherecar.rest.geoinfo.domain.GeoInfoFactory;
import com.wherecar.rest.geoinfo.infrastructure.GeoInfoReader;
import com.wherecar.rest.geoinfo.infrastructure.GeoInfoStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GeoInfoServiceImpl implements GeoInfoService {
    private final GeoInfoFactory geoInfoFactory;
    private final CompanyReader companyReader;
    private final GeoInfoReader geoInfoReader;
    private final GeoInfoStore geoInfoStore;


    public GeoInfoResponse createGeoInfo(Long companyId, GeoInfoRequest geoInfoRequest) {
        Company company = companyReader.getCompanyById(companyId);
        GeoInfo geoInfo = geoInfoFactory.toGeoInfo(geoInfoRequest, company);
        geoInfo = geoInfoStore.store(geoInfo);

        return geoInfoFactory.toGeoInfoResponse(geoInfo);
    }

    // todo: emulator GeoFence 정보 전송


    @Transactional(readOnly = true)
    public GeoInfoResponse getGeoInfo (Long geoInfoId) {
        GeoInfo geoInfo = geoInfoReader.getGeoInfoById(geoInfoId);
        return geoInfoFactory.toGeoInfoResponse(geoInfo);
    }


    public GeoInfoResponse updateGeoInfo(Long geoInfoId, GeoInfoRequest geoInfoRequest) {
        GeoInfo geoInfo = geoInfoReader.getGeoInfoById(geoInfoId);
        geoInfo.updateGeoInfo(geoInfoRequest);
        geoInfo = geoInfoStore.store(geoInfo);
        return geoInfoFactory.toGeoInfoResponse(geoInfo);

    }


    public void deleteGeoInfo(Long geoInfoId) {
        geoInfoStore.delete(geoInfoId);
    }

    public List<GeoInfoResponse> getGeoInfosByCompanyId(Long companyId) {
        List<GeoInfo> geoInfos = geoInfoReader.getGeoInfosByCompanyId(companyId);
        return geoInfoFactory.toGeoInfoResponses(geoInfos);
    }
}
