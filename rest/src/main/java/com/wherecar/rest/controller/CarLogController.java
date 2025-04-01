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

    //운행일지 목록 조회
    @GetMapping
    public ResponseEntity<List<CarLogsResponse>> carLogsGet(
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size) {

        List<CarLogsResponse> carLogs = carLogService.getCarLogs(page, size);
        return ResponseEntity.ok(carLogs);
    }

    //차량 아이디로 운행일지 목록 조회
    @GetMapping("/cars/{mdn}")
    public ResponseEntity<List<CarLogsResponse>> carLogsGetByCarId(
            @PathVariable String mdn,
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size) {

        List<CarLogsResponse> carLogs = carLogService.getCarLogsByCarId(mdn, page, size);
        return ResponseEntity.ok(carLogs);
    }

    //운행일지 상세 정보 조회
    @GetMapping("/{logId}")
    public ResponseEntity<CarLogDetailResponse> carLogsGetDetails(@PathVariable Long logId) {
        CarLogDetailResponse carLogs = carLogService.getCarLogsDetails(logId);
        return ResponseEntity.ok(carLogs);
    }

    //운행일지 상세 정보 수정
    @PutMapping("/{logId}")
    public ResponseEntity<String> carLogUpdateDetails(@PathVariable Long logId, @RequestBody CarLogsUpdateRequest carLogsUpdateRequest) {
        carLogService.updateCarLogDetails(logId, carLogsUpdateRequest);
        return ResponseEntity.ok("수정되었습니다.");
    }


    //운행일지 상세 정보 삭제
    @DeleteMapping("/{logId}")
    public ResponseEntity<String> carLogDeleteDetails(@PathVariable Long logId) {
        carLogService.deleteCarLogDetails(logId);
        return ResponseEntity.ok("삭제되었습니다.");
    }

}
