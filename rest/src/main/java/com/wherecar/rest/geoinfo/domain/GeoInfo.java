package com.wherecar.rest.geoinfo.domain;

import com.wherecar.rest.common.domain.BaseEntity;
import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.geoinfo.application.dto.GeoInfoRequest;
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
public class GeoInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="geo_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;

    @Column(name = "name")
    private String name;

    @Column(name = "geo_event_type")
    private String geoEventType;

    @Column(name = "geo_range")
    private String geoRange;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "on_time")
    private LocalDateTime onTime;

    @Column(name = "off_time")
    private LocalDateTime offTime;

    public void updateGeoInfo(GeoInfoRequest geoInfoRequest) {
        this.name = geoInfoRequest.getName();
        this.geoEventType = geoInfoRequest.getGeoEventType();
        this.geoRange = geoInfoRequest.getGeoRange();
        this.latitude = geoInfoRequest.getLatitude();
        this.longitude = geoInfoRequest.getLongitude();
        this.onTime = geoInfoRequest.getOnTime();
        this.offTime = geoInfoRequest.getOffTime();
    }
}
