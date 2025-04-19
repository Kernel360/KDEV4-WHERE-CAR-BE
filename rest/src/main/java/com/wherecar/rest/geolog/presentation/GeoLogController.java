package com.wherecar.rest.geolog.presentation;

import com.wherecar.rest.geolog.application.GeoLogService;
import com.wherecar.rest.geolog.application.dto.GeoLogRequest;
import com.wherecar.rest.geolog.application.dto.GeoLogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/geoLogs")
@RequiredArgsConstructor
public class GeoLogController {

    private final GeoLogService geoLogService;

    @GetMapping("/cars/{carId}")
    public ResponseEntity<List<GeoLogResponse>> geoLogGetByCarId(@PathVariable Long carId) {
        List<GeoLogResponse> geoLogResponses = geoLogService.getGeoLogsByCarId(carId);
        return ResponseEntity.ok(geoLogResponses);
    }

    @GetMapping("/{geoLogId}")
    public ResponseEntity<GeoLogResponse> geoLogGet(@PathVariable Long geoLogId) {
        GeoLogResponse geoLog = geoLogService.getGeoLog(geoLogId);
        return ResponseEntity.ok(geoLog);
    }

    @PutMapping("/{geoLogId}")
    public ResponseEntity<Void> geoLogUpdate(@PathVariable Long geoLogId, @RequestBody GeoLogRequest geoLogRequest) {
        geoLogService.updateGeoLog(geoLogId, geoLogRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{geoLogId}")
    public ResponseEntity<Void> geoLogDelete(@PathVariable Long geoLogId) {
        geoLogService.deleteGeoLog(geoLogId);
        return ResponseEntity.ok().build();
    }

}
