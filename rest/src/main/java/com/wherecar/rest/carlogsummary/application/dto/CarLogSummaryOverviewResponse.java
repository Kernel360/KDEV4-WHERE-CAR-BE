package com.wherecar.rest.carlogsummary.application.dto;

import com.wherecar.rest.gpslog.application.dto.GpsPoint;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogSummaryOverviewResponse {
    private Integer totalDistance;
    private Integer maxDistance;
    private Integer averageDistance;

    private Integer maxSpeed;
    private Integer averageSpeed;

    //주행시간관련
    private Integer totalDriveTime;
    private Integer maxDriveTime;
    private Integer averageDriveTime;

    //driveType 갯수 확인
    private Integer unclassifiedCount;
    private Integer commuteCount;
    private Integer businessCount;
    private Integer personalCount;



    //시동 on, off 정보
    private List<GpsPoint> onList;
    private List<GpsPoint> offList;
}
