package com.wherecar.collector.presentation;

import com.wherecar.collector.application.dto.CarLogRequest;
import com.wherecar.collector.application.dto.CarLogResponse;
import com.wherecar.collector.application.CarLogService;
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
public class CarLogController {

    private final CarLogService carLogService;

    @PostMapping("/on")
    public ResponseEntity<CarLogResponse> onLogReceive(@RequestBody CarLogRequest onLogRequest) {
        carLogService.receiveOnLog(onLogRequest);

        return ResponseEntity.ok(CarLogResponse.getCarLogResponse(onLogRequest.getMdn()));
    }

    @PostMapping("/off")
    public ResponseEntity<CarLogResponse> OffLogReceive(@RequestBody CarLogRequest offLogRequest) {
        carLogService.receiveOffLog(offLogRequest);

        return ResponseEntity.ok(CarLogResponse.getCarLogResponse(offLogRequest.getMdn()));
    }
}
