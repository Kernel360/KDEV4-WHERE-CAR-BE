package com.wherecar.rest.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="geofence_infos")
public class GeofenceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    private String geoEventType;
    private String geoRange;
    private String latitude;
    private String longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
