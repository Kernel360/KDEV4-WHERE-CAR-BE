package com.wherecar.rest.carlog.presentation;

import com.wherecar.rest.carlog.application.CarLogService;
import com.wherecar.rest.carlog.application.dto.CarLogFilterRequest;
import com.wherecar.rest.carlog.application.dto.CarLogResponse;
import com.wherecar.rest.carlog.application.dto.CarLogsUpdateRequest;
import com.wherecar.rest.common.constants.PaginationConstants;
import com.wherecar.rest.common.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Todo: 권한 체크 추후 추가 예정

@Slf4j
@RestController
@RequestMapping("/api/carLogs")
@RequiredArgsConstructor
class CarLogController {

    private final CarLogService carLogService;

    //운행일지 목록 조회 (filter 추가)
    @PostMapping
    public ResponseEntity<BaseResponse<Page<CarLogResponse>>> carLogsGetWithFilter(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size,
            @RequestBody(required = false) CarLogFilterRequest filterRequest) {

        Long companyId = (Long)httpServletRequest.getAttribute("companyId");

        CarLogFilterRequest request = filterRequest != null ? filterRequest : new CarLogFilterRequest();


        Page<CarLogResponse> carLogs = carLogService.getCarLogsFiltered(
                companyId,
                filterRequest.getMdn(),
                filterRequest.getStartTime(),
                filterRequest.getEndTime(),
                page,
                size
        );

        return BaseResponse.created(carLogs);
    }

    //운행일지 상세 정보 조회
    @GetMapping("/{logId}")
    public ResponseEntity<BaseResponse<CarLogResponse>> carLogsGetDetails(@PathVariable Long logId) {
        CarLogResponse carLogs = carLogService.getCarLogDetails(logId);
        return BaseResponse.ok(carLogs);
    }

    //운행일지 상세 정보 수정
    @PutMapping("/{logId}")
    public ResponseEntity<BaseResponse<CarLogResponse>> carLogUpdateDetails(@PathVariable Long logId, @RequestBody CarLogsUpdateRequest carLogsUpdateRequest) {
        CarLogResponse carLogResponse = carLogService.updateCarLogDetails(logId, carLogsUpdateRequest);
        return BaseResponse.created(carLogResponse);
    }


    //운행일지 상세 정보 삭제
    @DeleteMapping("/{logId}")
    public ResponseEntity<BaseResponse<Void>> carLogDeleteDetails(@PathVariable Long logId) {
        carLogService.deleteCarLogDetails(logId);
        return BaseResponse.ok();
    }

    // 대시보드 운행 통계 달별 km 및 운행건수를 counting
    @GetMapping("/statics")
    public ResponseEntity<BaseResponse<CarLogResponse>> carLogsStaticsGetAll(HttpServletRequest request) {

        Long companyId = (Long)request.getAttribute("companyId");
        System.out.println("companyId = " + companyId);
        CarLogResponse carLogsStaticsResponse = carLogService.getAllCarLogsStatics(companyId);

        return BaseResponse.ok(carLogsStaticsResponse);
    }

}
