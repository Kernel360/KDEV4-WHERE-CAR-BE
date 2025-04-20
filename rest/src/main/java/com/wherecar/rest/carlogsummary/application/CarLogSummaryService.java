package com.wherecar.rest.carlogsummary.application;

import com.wherecar.rest.carlogsummary.application.dto.CarLogSummaryOverviewResponse;

import java.time.LocalDateTime;

public interface CarLogSummaryService {
    CarLogSummaryOverviewResponse getCarLogSummaryOverviewByCompanyId(Long companyId, LocalDateTime from, LocalDateTime to);
    CarLogSummaryOverviewResponse getCarLogSummaryOverviewByMdn(String mdn, LocalDateTime from, LocalDateTime to);
}
