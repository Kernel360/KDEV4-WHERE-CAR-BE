package com.wherecar.rest.controller;

import com.wherecar.rest.dto.GpsLogResponse;
import com.wherecar.rest.service.GpsLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/gps")
@RequiredArgsConstructor
public class GpsLogController {

    private final GpsLogService gpsLogService;

    @GetMapping("/position")
    public ResponseEntity<GpsLogResponse> LatestLocationGet(@RequestParam String mdn) {
        GpsLogResponse gpsLogResponse = gpsLogService.getLatestLocation(mdn);
        return ResponseEntity.ok(gpsLogResponse);
    }

}
