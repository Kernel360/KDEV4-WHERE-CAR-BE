package com.wherecar.rest.carlog.domain;

import com.wherecar.rest.carlog.application.dto.CarLogResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CarLogFactory {

    private static final Integer METER_TO_KILOMETER = 1000;

    public CarLogResponse toCarLogResponse(CarLog carLog) {
        return CarLogResponse.builder()
                .logId(carLog.getId())
                .mdn(carLog.getMdn())
                .onTime(carLog.getOnTime())
                .offTime(carLog.getOffTime())
                .onMileage(carLog.getOnMileage())
                .offMileage(carLog.getOffMileage())
                .totalMileage(
                        carLog.getOffMileage() != null
                                ? (carLog.getOffMileage() - carLog.getOnMileage())
                                : 0D
                )
                .driver(carLog.getDriver())
                .driveType(carLog.getDriveType())
                .description(carLog.getDescription())
                .build();
    }

    //Todo: 추후 사용 예정 엑셀용 전체 반환 메소드
    public List<CarLogResponse> toCarLogsResponseList(List<CarLog> logs) {
        return logs.stream()
                .map(this::toCarLogResponse)
                .collect(Collectors.toList());
    }

    public Page<CarLogResponse> toCarLogsResponsePage(Page<CarLog> logs) {
        return logs.map(this::toCarLogResponse);
    }

}
