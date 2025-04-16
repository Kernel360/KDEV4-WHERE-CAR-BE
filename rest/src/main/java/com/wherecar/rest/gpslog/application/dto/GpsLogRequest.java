package com.wherecar.rest.gpslog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsLogRequest {
    private String mdn;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
