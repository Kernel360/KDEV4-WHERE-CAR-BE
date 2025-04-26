package com.wherecar.rest.carlog.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class CarLogFilterRequest {
    private String mdn;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}