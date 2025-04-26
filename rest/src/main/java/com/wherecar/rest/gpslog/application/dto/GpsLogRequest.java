package com.wherecar.rest.gpslog.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class GpsLogRequest {
    private String mdn;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
