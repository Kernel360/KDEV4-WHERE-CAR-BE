package com.wherecar.rest.carlogsummary.infrastructure;

import com.wherecar.rest.carlogsummary.domain.CarLogSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarLogSummaryReaderImpl implements CarLogSummaryReader {

    private final CarLogSummaryRepository carLogSummaryRepository;

    @Override
    public List<CarLogSummary> getCarLogSummariesByCompanyIdAndOffTimeBetween(Long companyId, LocalDateTime from, LocalDateTime to) {
        return carLogSummaryRepository.findByCompanyIdAndOffTimeBetween(companyId, from, to);
    }

    @Override
    public List<CarLogSummary> getCarLogSummariesByMdnAndOffTimeBetween(String mdn, LocalDateTime from, LocalDateTime to) {
        return carLogSummaryRepository.findByMdnAndOffTimeBetween(mdn, from, to);
    }
}
