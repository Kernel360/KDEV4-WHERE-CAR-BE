package com.wherecar.rest.gpslog.presentation;

import com.wherecar.rest.gpslog.application.dto.GpsLogRequest;
import com.wherecar.rest.gpslog.application.dto.GpsLogResponse;
import com.wherecar.rest.gpslog.application.dto.GpsRouteResponse;
import com.wherecar.rest.gpslog.application.GpsLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/gps")
@RequiredArgsConstructor
public class GpsLogController {

    private final GpsLogService gpsLogService;

    @GetMapping("/position")
    public ResponseEntity<GpsLogResponse> LocationGetLatest(@RequestParam String mdn) {
        GpsLogResponse gpsLogResponse = gpsLogService.getLatestGpsLogByMdn(mdn);
        return ResponseEntity.ok(gpsLogResponse);
    }

    @PostMapping("/route")
    public ResponseEntity<GpsRouteResponse> routeGet(
            @RequestBody GpsLogRequest request
    ) {
        log.info("routeGet request {}", request);
        return ResponseEntity.ok(gpsLogService
                .getGpsPointsByMdn(
                        request.getMdn(),
                        request.getStartTime(),
                        request.getEndTime()
                )
        );
    }

}
