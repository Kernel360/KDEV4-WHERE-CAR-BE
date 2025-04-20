package com.wherecar.rest.carlogsummary.infrastructure;

import com.wherecar.rest.carlogsummary.domain.CarLogSummary;

import java.time.LocalDateTime;
import java.util.List;

public interface CarLogSummaryReader {
    List<CarLogSummary> getCarLogSummariesByCompanyIdAndOffTimeBetween(Long companyId, LocalDateTime from, LocalDateTime to);
    List<CarLogSummary> getCarLogSummariesByMdnAndOffTimeBetween(String mdn, LocalDateTime from, LocalDateTime to);
}
