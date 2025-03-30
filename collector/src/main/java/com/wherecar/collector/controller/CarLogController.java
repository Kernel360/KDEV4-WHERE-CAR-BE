package com.wherecar.collector.controller;

import com.wherecar.collector.dto.CarLogRequest;
import com.wherecar.collector.dto.CarLogResponse;
import com.wherecar.collector.dto.ResponseCode;
import com.wherecar.collector.service.CarLogConverterService;
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
        carLogConverterService.receiveOnLog(onLogRequest);

        // TODO 일단 무조건 성공한다고 가정하고 작성. 그 외의 경우도 생각해 보기
        CarLogResponse onLogResponse = CarLogResponse.builder()
                .rstCd(ResponseCode.SUCCESS.getCode())
                .rstMsg(ResponseCode.SUCCESS.getMessage())
                .mdn(onLogRequest.getMdn())
                .build();

        return ResponseEntity.ok(onLogResponse);
    }

    // TODO DTO 내부의 mdn으로 차량을 조회할 것이기 때문에 @PathVariable로 id를 안 받았는데 괜찮은지 확인하기
    @PostMapping("/off")
    public ResponseEntity<CarLogResponse> OffLogReceive(
            @RequestBody CarLogRequest offLogRequest
    ) {
        carLogConverterService.receiveOffLog(offLogRequest);

        // TODO 일단 무조건 성공한다고 가정하고 작성. 그 외의 경우도 생각해 보기
        CarLogResponse offLogResponse = CarLogResponse.builder()
                .rstCd(ResponseCode.SUCCESS.getCode())
                .rstMsg(ResponseCode.SUCCESS.getMessage())
                .mdn(offLogRequest.getMdn())
                .build();

        return ResponseEntity.ok(offLogResponse);
    }
}
