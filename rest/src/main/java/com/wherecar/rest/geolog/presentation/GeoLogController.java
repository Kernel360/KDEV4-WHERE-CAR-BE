package com.wherecar.rest.geolog.presentation;

import com.wherecar.rest.common.response.BaseResponse;
import com.wherecar.rest.geolog.application.GeoLogService;
import com.wherecar.rest.geolog.application.dto.GeoLogRequest;
import com.wherecar.rest.geolog.application.dto.GeoLogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Todo: 권한 체크 추후 추가 예정

@Slf4j
@RestController
@RequestMapping("/api/geoLogs")
@RequiredArgsConstructor
public class GeoLogController {

    private final GeoLogService geoLogService;

    @GetMapping("/cars/{carId}")
    public ResponseEntity<BaseResponse<List<GeoLogResponse>>> geoLogGetByCarId(@PathVariable Long carId) {
        List<GeoLogResponse> geoLogResponses = geoLogService.getGeoLogsByCarId(carId);
        return BaseResponse.ok(geoLogResponses);
    }

    @GetMapping("/{geoLogId}")
    public ResponseEntity<BaseResponse<GeoLogResponse>> geoLogGet(@PathVariable Long geoLogId) {
        GeoLogResponse geoLogResponse = geoLogService.getGeoLog(geoLogId);
        return BaseResponse.ok(geoLogResponse);
    }

    @PutMapping("/{geoLogId}")
    public ResponseEntity<BaseResponse<GeoLogResponse>> geoLogUpdate(@PathVariable Long geoLogId, @RequestBody GeoLogRequest geoLogRequest) {
        GeoLogResponse geoLogResponse = geoLogService.updateGeoLog(geoLogId, geoLogRequest);
        return BaseResponse.created(geoLogResponse);
    }

    @DeleteMapping("/{geoLogId}")
    public ResponseEntity<BaseResponse<Void>> geoLogDelete(@PathVariable Long geoLogId) {
        geoLogService.deleteGeoLog(geoLogId);
        return BaseResponse.ok();
    }

}
