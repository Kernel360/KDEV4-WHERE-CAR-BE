package com.wherecar.rest.car.application.dto;

import com.wherecar.rest.car.domain.constant.AcquisitionType;
import com.wherecar.rest.car.domain.constant.OwnerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Setter
@Getter
@ToString
public class CarRegisterRequest {

    @NotBlank(message = "mdn은 필수입니다.")
    private String mdn;

    // TODO 필수인지 아닌지 여쭤 보기
    private String make;

    // TODO 필수인지 아닌지 여쭤 보기
    private String model;

    // TODO 필수인지 아닌지 여쭤 보기
    @Pattern(regexp = "^(19\\d{2}|20[0-1]\\d|202[0-6])$", message = "연도는 1900 ~ 2026 사이 값이어야 합니다.")
    private String year;

    // TODO 필수인지 아닌지 여쭤 보기
    private Double mileage;

    @NotNull(message = "ownerType은 필수입니다.")
    private OwnerType ownerType;

    @NotNull(message = "acquisitionType은 필수입니다.")
    private AcquisitionType acquisitionType;

    private Long companyId;

    @NotNull(message = "batteryVoltage는 필수입니다.")
    @Range(min = 0, max = 9999, message = "batteryVoltage는 0 이상 9999 이하여야 합니다.")
    private Integer batteryVoltage;

    //private GeoInfo geoInfo;
}
