package com.wherecar.rest.carlog.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wherecar.rest.carlog.domain.constant.DriveType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogDetailResponse {

    private Long logId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime onTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime offTime;

    private Integer onMileage;
    private Integer offMileage;
    private Integer totalMileage;
    private DriveType driveType;
    private String driver;
    private String description;

}
