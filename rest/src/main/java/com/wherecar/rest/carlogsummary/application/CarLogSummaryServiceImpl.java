package com.wherecar.rest.carlogsummary.application;

import com.wherecar.rest.carlogsummary.application.dto.CarLogSummaryOverviewResponse;
import com.wherecar.rest.carlogsummary.domain.CarLogSummary;
import com.wherecar.rest.carlogsummary.domain.CarLogSummaryFactory;
import com.wherecar.rest.carlogsummary.infrastructure.CarLogSummaryReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarLogSummaryServiceImpl implements CarLogSummaryService {
    private final CarLogSummaryReader carLogSummaryReader;
    private final CarLogSummaryFactory carLogSummaryFactory;

    @Override
    public CarLogSummaryOverviewResponse getCarLogSummaryOverviewByCompanyId(Long companyId, LocalDateTime from, LocalDateTime to) {
        List<CarLogSummary> carLogSummaries = carLogSummaryReader.getCarLogSummariesByCompanyIdAndOffTimeBetween(companyId, from, to);
        return carLogSummaryFactory.toCarLogSummaryOverviewResponse(carLogSummaries);
    }

    @Override
    public CarLogSummaryOverviewResponse getCarLogSummaryOverviewByMdn(String mdn, LocalDateTime from, LocalDateTime to) {
        List<CarLogSummary> carLogSummaries = carLogSummaryReader.getCarLogSummariesByMdnAndOffTimeBetween(mdn, from, to);
        return carLogSummaryFactory.toCarLogSummaryOverviewResponse(carLogSummaries);
    }
}
