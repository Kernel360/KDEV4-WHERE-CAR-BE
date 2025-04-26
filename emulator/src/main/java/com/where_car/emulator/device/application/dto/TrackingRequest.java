package com.where_car.emulator.device.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주기적으로 수집된 위치 정보를 담는 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrackingRequest {
    private Double latitude;
    private Double longitude;
    private LocalDateTime timeStamp;
}
