package com.wherecar.rest.car.application.dto;

import com.wherecar.rest.car.domain.AcquisitionType;
import com.wherecar.rest.car.domain.OwnerType;
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
    private Integer mileage;
    private OwnerType ownerType;
    private AcquisitionType acquisitionType;
    private Long companyId;
    private Integer batteryVoltage;

    //private GeoInfo geoInfo;
}
