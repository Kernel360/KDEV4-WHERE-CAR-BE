package com.wherecar.rest.controller;

import com.wherecar.rest.dto.GeoFenceLogRequest;
import com.wherecar.rest.dto.GeoFenceLogResponse;
import com.wherecar.rest.dto.GeoLogResponse;
import com.wherecar.rest.service.GeoLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/geoLog")
@RequiredArgsConstructor
public class GeoLogController {

    private final GeoLogService geoLogService;

    @GetMapping("/{id}")
    public ResponseEntity<GeoFenceLogResponse> geoLogGet(@PathVariable Long id) {
        GeoFenceLogResponse geoLog = geoLogService.getGeoLog(id);
        return ResponseEntity.ok(geoLog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> geoLogUpdate(@PathVariable Long id, @RequestBody GeoFenceLogRequest geoFenceLogRequest) {
        geoLogService.updateGeoLog(id, geoFenceLogRequest);
        return ResponseEntity.ok("수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> geoLogDelete(@PathVariable Long id) {
        geoLogService.deleteGeoLog(id);
        return ResponseEntity.ok("삭제되었습니다.");
    }

}
