package com.wherecar.rest.gpslog.presentation;

import com.wherecar.rest.common.response.BaseResponse;
import com.wherecar.rest.gpslog.application.GpsLogService;
import com.wherecar.rest.gpslog.application.dto.GpsLogRequest;
import com.wherecar.rest.gpslog.application.dto.GpsLogResponse;
import com.wherecar.rest.gpslog.application.dto.GpsRouteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Todo: 권한 체크 추후 추가 예정

@Slf4j
@RestController
@RequestMapping("/api/gps")
@RequiredArgsConstructor
public class GpsLogController {

    private final GpsLogService gpsLogService;

    @GetMapping("/position")
    public ResponseEntity<BaseResponse<GpsLogResponse>> LocationGetLatest(@RequestParam String mdn) {
        GpsLogResponse gpsLogResponse = gpsLogService.getLatestGpsLogByMdn(mdn);
        return BaseResponse.ok(gpsLogResponse);
    }

    @PostMapping("/route")
    public ResponseEntity<BaseResponse<GpsRouteResponse>> routeGet(@RequestBody GpsLogRequest request) {
        log.info("routeGet request {}", request);
        GpsRouteResponse gpsRouteResponse = gpsLogService.getGpsPointsByMdn(request.getMdn(), request.getStartTime(), request.getEndTime());
        return BaseResponse.created(gpsRouteResponse);
    }

}
