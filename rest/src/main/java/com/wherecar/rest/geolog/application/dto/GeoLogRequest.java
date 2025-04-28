package com.wherecar.rest.geolog.application.dto;

import com.wherecar.rest.geoinfo.application.dto.GeoInfoResponse;
import com.wherecar.rest.gpslog.domain.constant.GpsConditionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Setter
@Getter
@ToString
public class GeoLogRequest {

    private Long id;

    @NotBlank(message = "mdn은 필수입니다.")
    private String mdn;

    @Range(min = 0, max = 365, message = "angle은 0 이상 365 이하이어야 합니다.")
    private Integer angle;

    @Pattern(regexp = "^[12]$", message = "evaluateValue는 1 또는 2여야 합니다.")
    private String evaluateValue;

    @NotNull(message = "gpsCondition은 필수입니다.")
    private GpsConditionType gpsCondition;

    private Double latitude;

    private Double longitude;

    @Range(min = 0, max = 255, message = "speed는 0 이상 255 이하이어야 합니다.")
    private Integer speed;

    @Range(min = 0, max = 9999999, message = "sum은 0 이상 9,999,999 이하이어야 합니다.")
    private Integer sum;

    private GeoInfoResponse geoInfoResponse;
}
