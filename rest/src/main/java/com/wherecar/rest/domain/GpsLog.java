package com.wherecar.rest.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="gps_logs")
public class GpsLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="gps_log_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    //oTime+sec
    private LocalDateTime timestamp;

    private String gpsCondition;
    private Double latitude;
    private Double longitude;
    private Double angle;
    private Double speed;
    private Double sum;




}
