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
        log.info("[GeoInfo][GeoInfoServiceImpl][createGeoInfo] 시작 | companyId = {}, geoInfoRequest = {}", companyId, geoInfoRequest);

        Company company = companyReader.getCompanyById(companyId);
        GeoInfo geoInfo = geoInfoFactory.toGeoInfo(geoInfoRequest, company);
        geoInfo = geoInfoStore.store(geoInfo);

        GeoInfoResponse geoInfoResponse = geoInfoFactory.toGeoInfoResponse(geoInfo);
        log.info("[GeoInfo][GeoInfoServiceImpl][createGeoInfo] 종료 | geoInfoResponse = {}", geoInfoResponse);

        return geoInfoResponse;
    }

    // todo: emulator GeoFence 정보 전송


    @Transactional(readOnly = true)
    public GeoInfoResponse getGeoInfo (Long geoInfoId) {
        log.info("[GeoInfo][GeoInfoServiceImpl][getGeoInfo] 시작 | geoInfoId = {}", geoInfoId);

        GeoInfo geoInfo = geoInfoReader.getGeoInfoById(geoInfoId);
        GeoInfoResponse geoInfoResponse = geoInfoFactory.toGeoInfoResponse(geoInfo);

        log.info("[GeoInfo][GeoInfoServiceImpl][getGeoInfo] 종료 | geoInfoResponse = {}", geoInfoResponse);

        return geoInfoResponse;
    }


    public GeoInfoResponse updateGeoInfo(Long geoInfoId, GeoInfoRequest geoInfoRequest) {
        log.info("[GeoInfo][GeoInfoServiceImpl][updateGeoInfo] 시작 | geoInfoId = {}, geoInfoRequest = {}", geoInfoId, geoInfoRequest);

        GeoInfo geoInfo = geoInfoReader.getGeoInfoById(geoInfoId);
        geoInfo.updateGeoInfo(geoInfoRequest);
        geoInfo = geoInfoStore.store(geoInfo);
        GeoInfoResponse geoInfoResponse = geoInfoFactory.toGeoInfoResponse(geoInfo);

        log.info("[GeoInfo][GeoInfoServiceImpl][updateGeoInfo] 종료 | geoInfoResponse = {}", geoInfoResponse);

        return geoInfoResponse;
    }


    public void deleteGeoInfo(Long geoInfoId) {
        log.info("[GeoInfo][GeoInfoServiceImpl][deleteGeoInfo] 시작 | geoInfoId = {}", geoInfoId);

        geoInfoStore.delete(geoInfoId);

        log.info("[GeoInfo][GeoInfoServiceImpl][deleteGeoInfo] 종료");
    }

    public List<GeoInfoResponse> getGeoInfosByCompanyId(Long companyId) {
        log.info("[GeoInfo][GeoInfoServiceImpl][getGeoInfosByCompanyId] 시작 | companyId = {}", companyId);

        List<GeoInfo> geoInfos = geoInfoReader.getGeoInfosByCompanyId(companyId);
        List<GeoInfoResponse> geoInfoResponseList = geoInfoFactory.toGeoInfoResponses(geoInfos);

        log.info("[GeoInfo][GeoInfoServiceImpl][getGeoInfosByCompanyId] 종료 | geoInfoResponseList = {}", geoInfoResponseList);

        return geoInfoResponseList;
    }
}
