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
    private Integer latitude;
    private Integer longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;

    public void changeGeoEventType(String geoEventType) {
        this.geoEventType = geoEventType;
    }

    public void changeGeoRange(String geoRange) {
        this.geoRange = geoRange;
    }

    public void changeLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public void changeLongitude(Integer longitude) {
        this.longitude = longitude;
    }
}
