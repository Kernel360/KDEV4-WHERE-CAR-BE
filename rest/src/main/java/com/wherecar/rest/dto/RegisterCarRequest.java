package com.wherecar.rest.dto;

import com.wherecar.rest.domain.AcquisitionType;
import com.wherecar.rest.domain.Company;
import com.wherecar.rest.domain.OwnerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCarRequest {

    private String mdn;
    private String make;
    private String model;
    private String year;
    private Double mileage;
    private OwnerType ownerType;
    private AcquisitionType acquisitionType;
    private Long companyId;
    private Double batteryVoltage;

    //private GeoInfo geoInfo;
}
