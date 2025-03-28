package com.wherecar.rest.controller;

import com.wherecar.rest.constants.PaginationConstants;
import com.wherecar.rest.dto.CarLogDetailResponse;
import com.wherecar.rest.dto.CarLogsResponse;
import com.wherecar.rest.dto.CarLogsUpdateRequest;
import com.wherecar.rest.service.CarLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //운행일지 차량 상세 정보 조회
    @GetMapping("/{carId}")
    public ResponseEntity<List<CarLogDetailResponse>> carLogsGetDetails(
            @PathVariable Long carId,
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size) {

        List<CarLogDetailResponse> carLogs = carLogService.getCarLogsDetails(carId, page, size);
        return ResponseEntity.ok(carLogs);
    }

    //운행일지 차량 상세 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCarLogDetails(@PathVariable Long id, @RequestBody CarLogsUpdateRequest carLogsUpdateRequest) {
        carLogService.updateCarLogDetails(id, carLogsUpdateRequest);
        return ResponseEntity.ok("수정되었습니다.");
    }


}
