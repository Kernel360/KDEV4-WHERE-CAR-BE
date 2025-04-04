package com.wherecar.collector.controller;

import com.wherecar.collector.dto.*;
import com.wherecar.collector.service.GpsLogConverterService;
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

    private final GpsLogConverterService gpsLogConverterService;

    // TODO DTO 내부의 mdn으로 차량을 조회할 것이기 때문에 @PathVariable로 id를 안 받았는데 괜찮은지 확인하기
    @PostMapping("/gps")
    public ResponseEntity<GpsLogResponse> gpsLogReceive(
            @RequestBody GpsLogRequest gpsLogRequest
    ) {
        GpsLogResponse gpsLogResponse = gpsLogConverterService.receiveGpsLog(gpsLogRequest);

        return ResponseEntity.ok(gpsLogResponse);
    }
}
