package com.wherecar.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogsResponse {

    private Long carId;
    private String mdn;
    private String model;
    private Double mileage;
    //Todo: 차량 현황 추가(GPS)

}
