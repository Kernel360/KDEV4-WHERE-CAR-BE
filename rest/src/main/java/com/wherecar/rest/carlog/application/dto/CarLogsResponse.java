package com.wherecar.rest.carlog.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wherecar.rest.carlog.domain.DriveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogsResponse {

    private Long logId;

    private String mdn;

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

    // 대시보드 운행 통계 운행 건수
    private String carLogsCount;

    // 대시보드 월별 월간 주행거리
    private List<MonthlyMileage> monthlyMileages;
}
