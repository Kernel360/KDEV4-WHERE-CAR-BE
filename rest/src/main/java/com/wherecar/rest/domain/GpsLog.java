package com.wherecar.rest.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Table(name="gps_logs")
@Entity
@Builder
@Getter
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
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "gps_condition")
    private String gpsCondition;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "angle")
    private Integer angle;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "sum")
    private Integer sum;
}
