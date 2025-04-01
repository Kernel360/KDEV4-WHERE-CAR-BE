package com.wherecar.rest.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="geo_infos")
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GeoInfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="geo_info_id")
    private Long id;

    private String geoEventType;
    private String geoRange;
    // todo: Double -> Integer
    private Double latitude;
    private Double longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;

    public void changeGeoEventType(String geoEventType) {
        this.geoEventType = geoEventType;
    }

    public void changeGeoRange(String geoRange) {
        this.geoRange = geoRange;
    }

    public void changeLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void changeLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
