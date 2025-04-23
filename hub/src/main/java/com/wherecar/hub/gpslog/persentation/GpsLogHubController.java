package com.wherecar.hub.gpslog.persentation;

import com.wherecar.hub.gpslog.application.GpsLogHubService;
import com.wherecar.hub.gpslog.application.dto.GpsLogRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void sendGpsLogMessage(@RequestBody GpsLogRequest gpsLogRequest) {
        gpsLogHubService.sendGpsLogMessage(gpsLogRequest);
        // todo: return 수정예정 -> redis 토큰 검증 결과
    }
}
