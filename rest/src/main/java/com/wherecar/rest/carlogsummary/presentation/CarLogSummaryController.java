package com.wherecar.rest.carlogsummary.presentation;

import com.wherecar.rest.carlogsummary.application.CarLogSummaryService;
import com.wherecar.rest.carlogsummary.application.dto.CarLogSummaryOverviewResponse;
import com.wherecar.rest.common.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/api/stat")
@RequiredArgsConstructor
public class CarLogSummaryController {
    private final CarLogSummaryService carLogSummaryService;

    @GetMapping("/companies/my")
    public ResponseEntity<BaseResponse<CarLogSummaryOverviewResponse>> carLogSummaryOverviewGetByCompanyId(HttpServletRequest request, @RequestParam String from, @RequestParam String to) {
        Long companyId = (Long)request.getAttribute("companyId");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDateTime fromDateTime = LocalDate.parse(from, formatter).atStartOfDay();
        LocalDateTime toDateTime = LocalDate.parse(to, formatter).atTime(23, 59, 59);

        CarLogSummaryOverviewResponse overviewResponse =
                carLogSummaryService.getCarLogSummaryOverviewByCompanyId(companyId, fromDateTime, toDateTime);

        return BaseResponse.ok(overviewResponse);
    }

    @GetMapping("/mdn")
    public ResponseEntity<BaseResponse<CarLogSummaryOverviewResponse>> carLogSummaryOverviewGetByMdn(@RequestParam String mdn, @RequestParam String from, @RequestParam String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDateTime fromDateTime = LocalDate.parse(from, formatter).atStartOfDay();
        LocalDateTime toDateTime = LocalDate.parse(to, formatter).atTime(23, 59, 59);

        CarLogSummaryOverviewResponse overviewResponse = carLogSummaryService.getCarLogSummaryOverviewByMdn(mdn, fromDateTime, toDateTime);
        return BaseResponse.ok(overviewResponse);
    }
}
