package com.wherecar.rest.controller;

import com.wherecar.rest.constants.PaginationConstants;
import com.wherecar.rest.dto.CarLogDetailResponse;
import com.wherecar.rest.dto.CarLogFilterRequest;
import com.wherecar.rest.dto.CarLogsResponse;
import com.wherecar.rest.dto.CarLogsUpdateRequest;
import com.wherecar.rest.service.CarLogService;
import com.wherecar.rest.user.auth.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/carLogs")
@RequiredArgsConstructor
class CarLogController {

    private final CarLogService carLogService;

    //운행일지 목록 조회 (filter 추가)
    @PostMapping
    public ResponseEntity<Page<CarLogsResponse>> carLogsGetWithFilter(
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size,
            @RequestBody(required = false) CarLogFilterRequest filterRequest) {

        Long companyId = AuthUtil.getCompanyId();

        CarLogFilterRequest request = filterRequest != null ? filterRequest : new CarLogFilterRequest();


        Page<CarLogsResponse> carLogs = carLogService.getCarLogsFiltered(
                companyId,
                filterRequest.getMdn(),
                filterRequest.getStartTime(),
                filterRequest.getEndTime(),
                page,
                size
        );

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
    public ResponseEntity<Void> carLogUpdateDetails(@PathVariable Long logId, @RequestBody CarLogsUpdateRequest carLogsUpdateRequest) {
        carLogService.updateCarLogDetails(logId, carLogsUpdateRequest);
        return ResponseEntity.ok().build();
    }


    //운행일지 상세 정보 삭제
    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> carLogDeleteDetails(@PathVariable Long logId) {
        carLogService.deleteCarLogDetails(logId);
        return ResponseEntity.ok().build();
    }

}
