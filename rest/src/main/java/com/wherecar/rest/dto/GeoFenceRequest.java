package com.wherecar.rest.dto;

import com.wherecar.rest.domain.Company;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GeoFenceRequest {
    private String geoEventType;
    private String geoRange;
    private Double latitude;
    private Double longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
