package com.wherecar.rest.service;

import com.wherecar.rest.domain.Company;
import com.wherecar.rest.domain.GeoInfo;
import com.wherecar.rest.dto.GeoFenceResponse;
import com.wherecar.rest.dto.GeoFenceRequest;
import com.wherecar.rest.repository.CompanyRepository;
import com.wherecar.rest.repository.GeoInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GeoInfoServiceImpl implements GeoInfoService {

    private final GeoInfoRepository geoInfoRepository;
    private final CompanyRepository companyRepository;

    @Override
    public void createGeoInfo(GeoFenceRequest geoFenceRequest, Long companyId) {

        Company company = companyRepository.findById(companyId).orElseThrow();

        GeoInfo geoInfo = GeoInfo.builder()
                .company(company)
                .geoEventType(geoFenceRequest.getGeoEventType())
                .geoRange(geoFenceRequest.getGeoRange())
                .latitude(geoFenceRequest.getLatitude())
                .longitude(geoFenceRequest.getLongitude())
                .onTime(geoFenceRequest.getOnTime())
                .offTime(geoFenceRequest.getOffTime())
                .build();

        geoInfoRepository.save(geoInfo);

    }

    @Override
    @Transactional(readOnly = true)
    public List<GeoFenceResponse> getGeoFences(Long companyId, Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<GeoInfo> geoInfoPage = geoInfoRepository.findByCompanyId(companyId, pageRequest);

        return geoInfoPage.stream().map(geoInfo -> GeoFenceResponse.builder()
                        .id(geoInfo.getId())
                        .geoEventType(geoInfo.getGeoEventType())
                        .geoRange(geoInfo.getGeoRange())
                        .latitude(geoInfo.getLatitude())
                        .longitude(geoInfo.getLongitude())
                        .onTime(geoInfo.getOnTime())
                        .offTime(geoInfo.getOffTime())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public GeoFenceResponse getGeoInfo (Long id) {
        GeoInfo geoInfo = geoInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 조건의 지오펜스 정보를 찾을 수 없습니다."));

        return GeoFenceResponse.builder()
                .id(geoInfo.getId())
                .geoEventType(geoInfo.getGeoEventType())
                .geoRange(geoInfo.getGeoRange())
                .latitude(geoInfo.getLatitude())
                .longitude(geoInfo.getLongitude())
                .onTime(geoInfo.getOnTime())
                .offTime(geoInfo.getOffTime())
                .build();

    }

    @Override
    public void updateGeoInfo(Long id, GeoFenceRequest geoFenceRequest) {

        GeoInfo geoInfo = geoInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 지오펜스 정보를 찾을 수 없습니다."));

        geoInfo.changeGeoEventType(geoFenceRequest.getGeoEventType());
        geoInfo.changeGeoRange(geoFenceRequest.getGeoRange());
        geoInfo.changeLatitude(geoFenceRequest.getLatitude());
        geoInfo.changeLongitude(geoFenceRequest.getLongitude());

        geoInfoRepository.save(geoInfo);

    }

    @Override
    public void deleteGeoInfo(Long id) {

        geoInfoRepository.deleteById(id);

    }

    // todo: emulator GeoFence 정보 전송
}
