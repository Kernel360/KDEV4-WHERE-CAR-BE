package com.wherecar.rest.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
//Todo: 이름나중에 변경할것
public class CarLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    private String gpsCondition;
    private Double latitude;
    private Double longitude;
    private Double angle;
    private Double speed;
    private Double sum;

    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
