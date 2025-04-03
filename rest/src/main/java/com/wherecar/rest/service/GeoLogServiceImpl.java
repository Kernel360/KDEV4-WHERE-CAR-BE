package com.wherecar.rest.service;

import com.wherecar.rest.domain.Car;
import com.wherecar.rest.domain.GeoLog;
import com.wherecar.rest.dto.GeoLogRequest;
import com.wherecar.rest.dto.GeoLogResponse;
import com.wherecar.rest.dto.GeoInfoDTO;
import com.wherecar.rest.repository.CarRepository;
import com.wherecar.rest.repository.GeoLogRepository;
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
public class GeoLogServiceImpl implements GeoLogService {

    private final GeoLogRepository geoLogRepository;
    private final CarRepository carRepository;

    @Override
    public List<GeoLogResponse> getGeoLogByCarId(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow();
        List<GeoLog> geoLogs = geoLogRepository.getGeoLogByMdn(car.getMdn());
        List<GeoLogResponse> geoLogResponses = new ArrayList<>();
        for (GeoLog geoLog : geoLogs) {
            GeoLogResponse geoLogResponse = GeoLogResponse.builder()
                    .mdn(geoLog.getMdn())
                    .angle(geoLog.getAngle())
                    .angle(geoLog.getAngle())
                    .evaluateValue(geoLog.getEvaluateValue())
                    .gpsCondition(geoLog.getGpsCondition())
                    .latitude(geoLog.getLatitude())
                    .longitude(geoLog.getLongitude())
                    .oTime(String.valueOf(geoLog.getOTime()))
                    .speed(Double.valueOf(geoLog.getSpeed()))
                    .sum(Double.valueOf(geoLog.getSum()))
                    .mdn(geoLog.getMdn())
                    .geoInfoDTO(GeoInfoDTO.builder()
                            .geoEventType(geoLog.getGeoInfo().getGeoEventType())
                            .geoRange(geoLog.getGeoInfo().getGeoRange())
                            .latitude(geoLog.getGeoInfo().getLatitude())
                            .longitude(geoLog.getGeoInfo().getLongitude())
                            .onTime(geoLog.getGeoInfo().getOnTime())
                            .offTime(geoLog.getGeoInfo().getOffTime())
                            .build())
                    .build();
            geoLogResponses.add(geoLogResponse);
        }
        return geoLogResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public GeoLogResponse getGeoLog(Long id) {
        GeoLog geoLog =  geoLogRepository.findById(id).orElseThrow();

        log.info("geoLog : {}", geoLog);

        return GeoLogResponse.builder()
                .id(geoLog.getId())
                .angle(geoLog.getAngle())
                .evaluateValue(geoLog.getEvaluateValue())
                .gpsCondition(geoLog.getGpsCondition())
                .latitude(geoLog.getLatitude())
                .longitude(geoLog.getLongitude())
                .oTime(String.valueOf(geoLog.getOTime()))
                .speed(Double.valueOf(geoLog.getSpeed()))
                .sum(Double.valueOf(geoLog.getSum()))
                .mdn(geoLog.getMdn())
                .geoInfoDTO(GeoInfoDTO.builder()
                        .geoEventType(geoLog.getGeoInfo().getGeoEventType())
                        .geoRange(geoLog.getGeoInfo().getGeoRange())
                        .latitude(geoLog.getGeoInfo().getLatitude())
                        .longitude(geoLog.getGeoInfo().getLongitude())
                        .onTime(geoLog.getGeoInfo().getOnTime())
                        .offTime(geoLog.getGeoInfo().getOffTime())
                        .build())
                .build();
    }

    @Override
    public void updateGeoLog(Long id, GeoLogRequest geoLogRequest) {

        GeoLog geoLog =  geoLogRepository.findById(id).orElseThrow();

        geoLog.changeMdn(geoLogRequest.getMdn());
        geoLog.changeAngle(geoLogRequest.getAngle());
        geoLog.changeEvaluate_value(String.valueOf(geoLogRequest.getEvaluateValue()));
        geoLog.changeGpsCondition(geoLogRequest.getGpsCondition());
        geoLog.changeLatitude(geoLogRequest.getLatitude());
        geoLog.changeLongitude(geoLogRequest.getLongitude());
        geoLog.changeSpeed(geoLogRequest.getSpeed());
        geoLog.changeSum(geoLogRequest.getSum());

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
