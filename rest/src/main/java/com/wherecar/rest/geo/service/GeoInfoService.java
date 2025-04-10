package com.wherecar.rest.geo.service;

import com.wherecar.rest.domain.Company;
import com.wherecar.rest.geo.domain.GeoInfo;
import com.wherecar.rest.geo.dto.GeoInfoRequest;
import com.wherecar.rest.geo.dto.GeoInfoResponse;
import com.wherecar.rest.geo.repository.GeoInfoRepository;
import com.wherecar.rest.repository.CompanyRepository;
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
public class GeoInfoService {

    private final GeoInfoRepository geoInfoRepository;
    private final CompanyRepository companyRepository;

    public void createGeoInfo(GeoInfoRequest geoInfoRequest, Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow();
        GeoInfo geoInfo = GeoInfo.builder()
                .name(geoInfoRequest.getName())
                .geoEventType(geoInfoRequest.getGeoEventType())
                .geoRange(geoInfoRequest.getGeoRange())
                .latitude(geoInfoRequest.getLatitude())
                .longitude(geoInfoRequest.getLongitude())
                .onTime(geoInfoRequest.getOnTime())
                .offTime(geoInfoRequest.getOffTime())
                .company(company)
                .build();

        geoInfoRepository.save(geoInfo);

    }

    // todo: emulator GeoFence 정보 전송


    @Transactional(readOnly = true)
    public GeoInfoResponse getGeoInfo (Long id) {
        GeoInfo geoInfo = geoInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 조건의 지오펜스 정보를 찾을 수 없습니다."));

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


    public void updateGeoInfo(Long id, GeoInfoRequest geoInfoRequest) {

        GeoInfo geoInfo = geoInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 지오펜스 정보를 찾을 수 없습니다."));

        geoInfo.changeName(geoInfoRequest.getName());
        geoInfo.changeGeoEventType(geoInfoRequest.getGeoEventType());
        geoInfo.changeGeoRange(geoInfoRequest.getGeoRange());
        geoInfo.changeLatitude(geoInfoRequest.getLatitude());
        geoInfo.changeLongitude(geoInfoRequest.getLongitude());

        geoInfoRepository.save(geoInfo);

    }


    public void deleteGeoInfo(Long id) {

        geoInfoRepository.deleteById(id);

    }

    public List<GeoInfoResponse> getGeoInfosByCompanyId(Long companyId) {
        List<GeoInfo> geoInfos = geoInfoRepository.findByCompanyId(companyId);
        List<GeoInfoResponse> geoInfoResponses = new ArrayList<>();
        for(GeoInfo geoInfo : geoInfos) {
            GeoInfoResponse geoInfoResponse = GeoInfoResponse.builder()
                    .id(geoInfo.getId())
                    .name(geoInfo.getName())
                    .geoEventType(geoInfo.getGeoEventType())
                    .geoRange(geoInfo.getGeoRange())
                    .latitude(geoInfo.getLatitude())
                    .longitude(geoInfo.getLongitude())
                    .onTime(geoInfo.getOnTime())
                    .offTime(geoInfo.getOffTime())
                    .build();
            geoInfoResponses.add(geoInfoResponse);
        }
        return geoInfoResponses;
    }

}
