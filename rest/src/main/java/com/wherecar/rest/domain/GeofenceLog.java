package com.wherecar.rest.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="geofence_logs")
public class GeofenceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="log_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;


    private LocalDateTime oTime;

    private String gpsCondition;
    private Double latitude;
    private Double longitude;
    private Double angle;
    private Double speed;
    private Double sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="info_id")
    private GeofenceInfo info;

    private String evaluateValue;

}
