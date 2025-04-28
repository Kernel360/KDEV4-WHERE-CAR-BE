package com.wherecar.rest.gpslog.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class GpsLogRequest {

    @NotBlank(message = "mdn은 필수입니다.")
    private String mdn;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
