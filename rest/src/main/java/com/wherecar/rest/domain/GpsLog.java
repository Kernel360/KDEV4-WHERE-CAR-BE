package com.wherecar.rest.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Table(name="gps_logs")
@Entity
@Builder
@ToString(exclude = "car")
@NoArgsConstructor
@AllArgsConstructor
public class GpsLog extends BaseEntity{
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
