package com.wherecar.rest.controller;

import com.wherecar.rest.dto.GeoLogRequest;
import com.wherecar.rest.dto.GeoLogResponse;
import com.wherecar.rest.service.GeoLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geoLog")
@RequiredArgsConstructor
public class GeoLogController {

    private final GeoLogService geoLogService;

    @GetMapping("/cars/{carId}")
    public ResponseEntity<List<GeoLogResponse>> geoLogGetByCarId(@PathVariable Long carId) {
        List<GeoLogResponse> geoLogResponses = geoLogService.getGeoLogByCarId(carId);
        return ResponseEntity.ok(geoLogResponses);
    }

    @GetMapping("/{geoLogId}")
    public ResponseEntity<GeoLogResponse> geoLogGet(@PathVariable Long geoLogId) {
        GeoLogResponse geoLog = geoLogService.getGeoLog(geoLogId);
        return ResponseEntity.ok(geoLog);
    }

    @PutMapping("/{geoLogId}")
    public ResponseEntity<String> geoLogUpdate(@PathVariable Long geoLogId, @RequestBody GeoLogRequest geoLogRequest) {
        geoLogService.updateGeoLog(geoLogId, geoLogRequest);
        return ResponseEntity.ok("수정되었습니다.");
    }

    @DeleteMapping("/{geoLogId}")
    public ResponseEntity<String> geoLogDelete(@PathVariable Long geoLogId) {
        geoLogService.deleteGeoLog(geoLogId);
        return ResponseEntity.ok("삭제되었습니다.");
    }

}
