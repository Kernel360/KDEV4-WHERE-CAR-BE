package com.wherecar.hub.gpslog.persentation;

import com.wherecar.hub.gpslog.application.GpsLogHubService;
import com.wherecar.hub.gpslog.application.dto.GpsLogRequest;
import com.wherecar.hub.gpslog.application.dto.GpsLogResponse;
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
@RequestMapping("/api/hub")
@RequiredArgsConstructor
public class GpsLogHubController {

    private final GpsLogHubService gpsLogHubService;

    @PostMapping("/gps")
    public ResponseEntity<GpsLogResponse> sendGpsLogMessage(@RequestBody @Valid GpsLogRequest gpsLogRequest) {
        gpsLogHubService.sendGpsLogMessage(gpsLogRequest);
        return ResponseEntity.ok(GpsLogResponse.getGpsLogResponse(gpsLogRequest.getMdn()));
    }
}