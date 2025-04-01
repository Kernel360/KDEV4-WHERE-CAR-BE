package com.wherecar.rest.service;

import com.wherecar.rest.domain.GeoInfo;
import com.wherecar.rest.dto.GeoFenceResponse;
import com.wherecar.rest.dto.GeoInfoRegistRequest;
import com.wherecar.rest.dto.GeoInfoRequest;
import com.wherecar.rest.dto.GeoInfoResponse;
import com.wherecar.rest.repository.GeoInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .latitude(Double.valueOf(geoInfoRegistRequest.getLatitude()))
                .longitude(Double.valueOf(geoInfoRegistRequest.getLongitude()))
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
        // todo: Double -> Integer 변경예정
        geoInfo.changeLatitude(Double.valueOf(geoInfoRegistRequest.getLatitude()));
        geoInfo.changeLongitude(Double.valueOf(geoInfoRegistRequest.getLongitude()));

        geoInfoRepository.save(geoInfo);

    }

    @Override
    public void deleteGeoInfo(Long id) {

        geoInfoRepository.deleteById(id);

    }

}
