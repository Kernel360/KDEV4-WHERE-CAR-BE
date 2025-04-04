package com.wherecar.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GpsPoint {
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}