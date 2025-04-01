package com.wherecar.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wherecar.rest.domain.CarState;
import com.wherecar.rest.domain.DriveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogsResponse {

    private Long LogId;

    private String mdn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime onTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime offTime;

    private Double onMileage;
    private Double offMileage;
    private Double totalMileage;
    private DriveType driveType;
    private String driver;
    private String description;
    private CarState carStatus;


}
