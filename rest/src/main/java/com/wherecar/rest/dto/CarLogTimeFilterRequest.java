package com.wherecar.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogTimeFilterRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
