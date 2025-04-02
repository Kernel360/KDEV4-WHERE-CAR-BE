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

    @Column(name = "geo_event_type")
    private String geoEventType;

    @Column(name = "geo_range")
    private String geoRange;


    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longitude")
    private Integer longitude;

    @Column(name = "on_time")
    private LocalDateTime onTime;

    @Column(name = "off_time")
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
