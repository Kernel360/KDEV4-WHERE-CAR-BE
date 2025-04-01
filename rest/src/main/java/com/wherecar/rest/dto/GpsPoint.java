package com.wherecar.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GpsPoint {
    private double latitude;     // 위도
    private double longitude;    // 경도
    private LocalDateTime timestamp; // 해당 위치의 시간
}