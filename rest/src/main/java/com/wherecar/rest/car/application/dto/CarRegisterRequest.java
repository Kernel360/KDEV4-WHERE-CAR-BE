package com.wherecar.rest.car.application.dto;

import com.wherecar.rest.car.domain.constant.AcquisitionType;
import com.wherecar.rest.car.domain.constant.OwnerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarRegisterRequest {

    private String mdn;
    private String make;
    private String model;
    private String year;
    private Double mileage;
    private OwnerType ownerType;
    private AcquisitionType acquisitionType;
    private Long companyId;
    private Integer batteryVoltage;

    //private GeoInfo geoInfo;
}
