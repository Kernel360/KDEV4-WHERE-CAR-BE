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
        gpsLogConverterService.receiveGpsLog(gpsLogRequest);

        // TODO 일단 무조건 성공한다고 가정하고 작성. 그 외의 경우도 생각해 보기
        GpsLogResponse gpsLogResponse = GpsLogResponse.builder()
                .rstCd(ResponseCode.SUCCESS.getCode())
                .rstMsg(ResponseCode.SUCCESS.getMessage())
                .mdn(gpsLogRequest.getMdn())
                .build();

        return ResponseEntity.ok(gpsLogResponse);
    }
}
