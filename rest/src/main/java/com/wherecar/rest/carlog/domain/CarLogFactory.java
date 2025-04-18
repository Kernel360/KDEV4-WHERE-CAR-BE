package com.wherecar.rest.carlog.domain;

import com.wherecar.rest.carlog.application.dto.CarLogDetailResponse;
import com.wherecar.rest.carlog.application.dto.CarLogsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CarLogFactory {

    private static final Integer METER_TO_KILOMETER = 1000;

    public CarLogDetailResponse toCarLogDetailResponse(CarLog carLog) {
        return CarLogDetailResponse.builder()
                .logId(carLog.getId())
                .onTime(carLog.getOnTime())
                .offTime(carLog.getOffTime())
                .onMileage(carLog.getOnMileage())
                .offMileage(carLog.getOffMileage())
                .totalMileage((carLog.getOffMileage() - carLog.getOnMileage()) / METER_TO_KILOMETER)
                .driveType(carLog.getDriveType())
                .description(carLog.getDescription())
                .driver(carLog.getDriver())
                .build();
    }


    public CarLogsResponse toCarLogsResponse(CarLog carLog) {
        return CarLogsResponse.builder()
                .logId(carLog.getId())
                .mdn(carLog.getMdn())
                .onTime(carLog.getOnTime())
                .offTime(carLog.getOffTime())
                .onMileage(carLog.getOnMileage())
                .offMileage(carLog.getOffMileage())
                .totalMileage(carLog.getOffMileage() - carLog.getOnMileage())  // 총 주행거리 계산
                .driver(carLog.getDriver())
                .driveType(carLog.getDriveType())
                .description(carLog.getDescription())
                .build();
    }

    //Todo: 추후 사용 예정 엑셀용 전체 반환 메소드
    public List<CarLogsResponse> toCarLogsResponseList(List<CarLog> logs) {
        return logs.stream()
                .map(this::toCarLogsResponse)
                .collect(Collectors.toList());
    }

    public Page<CarLogsResponse> toCarLogsResponsePage(Page<CarLog> logs) {
        return logs.map(this::toCarLogsResponse);
    }

}
