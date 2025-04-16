package com.wherecar.collector.presentation;

import com.wherecar.collector.application.dto.CarLogRequest;
import com.wherecar.collector.application.dto.CarLogResponse;
import com.wherecar.collector.application.CarLogConverterService;
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

    private final CarLogConverterService carLogConverterService;

    // TODO DTO 내부의 mdn으로 차량을 조회할 것이기 때문에 @PathVariable로 id를 안 받았는데 괜찮은지 확인하기
    @PostMapping("/on")
    public ResponseEntity<CarLogResponse> onLogReceive(
            @RequestBody CarLogRequest onLogRequest
    ) {
        CarLogResponse onLogResponse = carLogConverterService.receiveOnLog(onLogRequest);

        return ResponseEntity.ok(onLogResponse);
    }

    // TODO DTO 내부의 mdn으로 차량을 조회할 것이기 때문에 @PathVariable로 id를 안 받았는데 괜찮은지 확인하기
    @PostMapping("/off")
    public ResponseEntity<CarLogResponse> OffLogReceive(
            @RequestBody CarLogRequest offLogRequest
    ) {
        CarLogResponse offLogResponse = carLogConverterService.receiveOffLog(offLogRequest);

        return ResponseEntity.ok(offLogResponse);
    }
}
