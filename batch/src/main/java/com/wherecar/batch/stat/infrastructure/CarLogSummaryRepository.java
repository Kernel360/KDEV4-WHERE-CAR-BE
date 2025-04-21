package com.wherecar.batch.stat.infrastructure;

import com.wherecar.batch.stat.domain.CarLogSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarLogSummaryRepository extends JpaRepository<CarLogSummary, Long> {
}
