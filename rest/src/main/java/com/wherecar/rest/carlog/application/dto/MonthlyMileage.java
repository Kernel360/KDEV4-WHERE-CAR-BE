package com.wherecar.rest.carlog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyMileage {
    private String month;           // "2025-04"
    private Double totalMileage;
}
