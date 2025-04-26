package com.wherecar.rest.carlog.application.dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyMileage {
    private String month;           // "2025-04"
    private Double totalMileage;
}
