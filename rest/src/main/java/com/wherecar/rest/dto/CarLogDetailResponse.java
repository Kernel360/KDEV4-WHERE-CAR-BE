package com.wherecar.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wherecar.rest.domain.CarStatus;
import com.wherecar.rest.domain.DriveType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogDetailResponse {

    private Long LogId;

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
    private CarStatus carStatus;

}
