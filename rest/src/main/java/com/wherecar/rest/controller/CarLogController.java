package com.wherecar.rest.controller;

import com.wherecar.rest.constants.PaginationConstants;
import com.wherecar.rest.dto.CarLogsResponse;
import com.wherecar.rest.dto.CarResponse;
import com.wherecar.rest.service.CarLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/carLogs")
@RequiredArgsConstructor
class CarLogController {

    private final CarLogService carLogService;

    //운행일지 차량 목록 조회
    @GetMapping
    public ResponseEntity<List<CarLogsResponse>> carLogsGet(
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size) {

        List<CarLogsResponse> carLogs = carLogService.getCarLogs(page, size);
        return ResponseEntity.ok(carLogs);
    }

}
