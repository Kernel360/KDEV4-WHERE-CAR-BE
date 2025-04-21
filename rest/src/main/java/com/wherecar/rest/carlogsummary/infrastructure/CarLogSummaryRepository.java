package com.wherecar.rest.carlogsummary.infrastructure;

import com.wherecar.rest.carlogsummary.domain.CarLogSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CarLogSummaryRepository extends JpaRepository<CarLogSummary, Long> {
    List<CarLogSummary> findByCompanyIdAndOffTimeBetween(Long companyId, LocalDateTime from, LocalDateTime to);
    List<CarLogSummary> findByMdnAndOffTimeBetween(String mdn, LocalDateTime from, LocalDateTime to);
}
