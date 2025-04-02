package com.wherecar.rest.service;

import com.wherecar.rest.domain.GeoLog;
import com.wherecar.rest.dto.GeoFenceLogRequest;
import com.wherecar.rest.dto.GeoFenceLogResponse;
import com.wherecar.rest.repository.GeoLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GeoLogServiceImpl implements GeoLogService {

    private final GeoLogRepository geoLogRepository;

    @Override
    @Transactional(readOnly = true)
    public GeoFenceLogResponse getGeoLog(Long id) {
        GeoLog geoLog =  geoLogRepository.findById(id).orElseThrow();

        log.info("geoLog : {}", geoLog);

        return GeoFenceLogResponse.builder()
                .id(geoLog.getId())
                .angle(Double.valueOf(geoLog.getAngle()))
                .evaluate_value(geoLog.getEvaluateValue())
                .gps_condition(geoLog.getGpsCondition())
                .latitude(geoLog.getLatitude())
                .longitude(geoLog.getLongitude())
                .o_time(String.valueOf(geoLog.getOTime()))
                .speed(Double.valueOf(geoLog.getSpeed()))
                .sum(Double.valueOf(geoLog.getSum()))
                .mdn(geoLog.getMdn())
                .geoInfo(geoLog.getGeoInfo())
                .build();
    }

    @Override
    public void updateGeoLog(Long id, GeoFenceLogRequest geoFenceLogRequest) {

        GeoLog geoLog =  geoLogRepository.findById(id).orElseThrow();

        geoLog.changeMdn(geoFenceLogRequest.getMdn());
        geoLog.changeAngle(geoFenceLogRequest.getAngle());
        geoLog.changeEvaluate_value(String.valueOf(geoFenceLogRequest.getEvaluate_value()));
        geoLog.changeGps_condition(geoFenceLogRequest.getGps_condition());
        geoLog.changeLatitude(geoFenceLogRequest.getLatitude());
        geoLog.changeLongitude(geoFenceLogRequest.getLongitude());
        geoLog.changeSpeed(geoFenceLogRequest.getSpeed());
        geoLog.changeSum(geoFenceLogRequest.getSum());

        geoLogRepository.save(geoLog);
    }

    @Override
    public void deleteGeoLog(Long id) {
        if(!geoLogRepository.existsById(id)){
            throw new RuntimeException("해당 로그에 대한 정보를 찾을 수 없습니다.");
        }
        geoLogRepository.deleteById(id);
    }
}
