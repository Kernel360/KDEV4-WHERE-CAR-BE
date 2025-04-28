package com.wherecar.collector.gpslog.presentation;

import com.wherecar.collector.gpslog.application.dto.GpsLogRequest;
import com.wherecar.collector.gpslog.application.dto.GpsLogResponse;
import com.wherecar.collector.gpslog.application.GpsLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GpsLogController {

    private final GpsLogService gpsLogService;

    @PostMapping("/gps")
    public ResponseEntity<GpsLogResponse> gpsLogReceive(@RequestBody @Valid GpsLogRequest gpsLogRequest) {
        gpsLogService.receiveGpsLogs(gpsLogRequest);

        return ResponseEntity.ok(GpsLogResponse.getGpsLogResponse(gpsLogRequest.getMdn()));
    }
}
