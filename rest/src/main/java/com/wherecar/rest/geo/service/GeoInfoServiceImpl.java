package com.wherecar.rest.geo.service;

import com.wherecar.rest.geo.domain.GeoInfo;
import com.wherecar.rest.geo.dto.GeoFenceResponse;
import com.wherecar.rest.geo.dto.GeoInfoRegistRequest;
import com.wherecar.rest.geo.repository.GeoInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GeoInfoServiceImpl implements GeoInfoService {

    private final GeoInfoRepository geoInfoRepository;

    @Override
    public void createGeoInfo(GeoInfoRegistRequest geoInfoRegistRequest) {

        GeoInfo geoInfo = GeoInfo.builder()
                .geoEventType(geoInfoRegistRequest.getGeoEventType())
                .geoRange(geoInfoRegistRequest.getGeoRange())
                .latitude(Integer.valueOf(geoInfoRegistRequest.getLatitude()))
                .longitude(Integer.valueOf(geoInfoRegistRequest.getLongitude()))
                .onTime(geoInfoRegistRequest.getOnTime())
                .offTime(geoInfoRegistRequest.getOffTime())
                .build();

        geoInfoRepository.save(geoInfo);

    }

    // todo: emulator GeoFence 정보 전송

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
    public void updateGeoInfo(Long id, GeoInfoRegistRequest geoInfoRegistRequest) {

        GeoInfo geoInfo = geoInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 지오펜스 정보를 찾을 수 없습니다."));

        geoInfo.changeGeoEventType(geoInfoRegistRequest.getGeoEventType());
        geoInfo.changeGeoRange(geoInfoRegistRequest.getGeoRange());
        geoInfo.changeLatitude(Integer.valueOf(geoInfoRegistRequest.getLatitude()));
        geoInfo.changeLongitude(Integer.valueOf(geoInfoRegistRequest.getLongitude()));

        geoInfoRepository.save(geoInfo);

    }

    @Override
    public void deleteGeoInfo(Long id) {

        geoInfoRepository.deleteById(id);

    }

}
