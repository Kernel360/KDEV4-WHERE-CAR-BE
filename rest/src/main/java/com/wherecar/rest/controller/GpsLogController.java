package com.wherecar.rest.controller;

import com.wherecar.rest.dto.GpsLogRequest;
import com.wherecar.rest.dto.GpsLogResponse;
import com.wherecar.rest.dto.GpsRouteResponse;
import com.wherecar.rest.service.GpsLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gps")
@RequiredArgsConstructor
public class GpsLogController {

    private final GpsLogService gpsLogService;

    @GetMapping("/position")
    public ResponseEntity<GpsLogResponse> LocationGetLatest(@RequestParam String mdn) {
        GpsLogResponse gpsLogResponse = gpsLogService.getLatestLocation(mdn);
        return ResponseEntity.ok(gpsLogResponse);
    }

    @PostMapping("/route")
    public ResponseEntity<GpsRouteResponse> routeGet(
            @RequestBody GpsLogRequest request
    ) {
        return ResponseEntity.ok(gpsLogService
                .getRoute(
                        request.getMdn(),
                        request.getStartTime(),
                        request.getEndTime()
                )
        );
    }

}
