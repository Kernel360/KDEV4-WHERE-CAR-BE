package com.wherecar.rest.carlogsummary.domain;

import com.wherecar.rest.carlogsummary.application.dto.CarLogSummaryOverviewResponse;
import com.wherecar.rest.gpslog.application.dto.GpsPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CarLogSummaryFactory {
    public CarLogSummaryOverviewResponse toCarLogSummaryOverviewResponse(List<CarLogSummary> carLogSummaries) {
        int totalDistance = 0;
        int maxDistance = 0;
        int averageDistance;

        int maxSpeed = 0;
        int averageSpeed;

        int totalDriveTime = 0;
        int maxDriveTime = 0;
        int averageDriveTime;

        int unclassifiedCount = 0;
        int commuteCount = 0;
        int businessCount = 0;
        int personalCount = 0;

        List<GpsPoint> onList = new ArrayList<>();
        List<GpsPoint> offList = new ArrayList<>();

        for(CarLogSummary carLogSummary : carLogSummaries) {
            maxDistance = Math.max(maxDistance, carLogSummary.getDistance());
            maxSpeed = Math.max(maxSpeed, carLogSummary.getMaxSpeed());
            maxDriveTime = Math.max(maxDriveTime, (int) Duration.between(carLogSummary.getOnTime(),carLogSummary.getOffTime()).toSeconds());

            totalDistance += carLogSummary.getDistance();
            totalDriveTime += (int) Duration.between(carLogSummary.getOnTime(),carLogSummary.getOffTime()).toSeconds();

            if(carLogSummary.getDriveType()==null){
                unclassifiedCount++;
            } else {
                switch (carLogSummary.getDriveType()) {
                    case UNCLASSIFIED -> unclassifiedCount++;
                    case PERSONAL     -> personalCount++;
                    case COMMUTE      -> commuteCount++;
                    case BUSINESS     -> businessCount++;
                }
            }



            onList.add(GpsPoint.builder()
                            .latitude(carLogSummary.getOnLatitude())
                            .longitude(carLogSummary.getOnLongitude())
                            .timestamp(carLogSummary.getOnTime())
                    .build()
            );

            offList.add(GpsPoint.builder()
                    .latitude(carLogSummary.getOffLatitude())
                    .longitude(carLogSummary.getOffLongitude())
                    .timestamp(carLogSummary.getOffTime())
                    .build()
            );
        }

        try{
            averageDistance = totalDistance / carLogSummaries.size();
            averageSpeed = (totalDistance * 3600) / totalDriveTime;
            averageDriveTime = totalDriveTime / carLogSummaries.size();
        } catch (Exception e) {
            averageDistance = 0;
            averageSpeed = 0;
            averageDriveTime = 0;
        }




        return CarLogSummaryOverviewResponse.builder()
                .totalDistance(totalDistance)
                .maxDistance(maxDistance)
                .averageDistance(averageDistance)
                .maxSpeed(maxSpeed)
                .averageSpeed(averageSpeed)
                .totalDriveTime(totalDriveTime)
                .maxDriveTime(maxDriveTime)
                .averageDriveTime(averageDriveTime)
                .unclassifiedCount(unclassifiedCount)
                .commuteCount(commuteCount)
                .businessCount(businessCount)
                .personalCount(personalCount)
                .onList(onList)
                .offList(offList)
                .build();
    }
}
