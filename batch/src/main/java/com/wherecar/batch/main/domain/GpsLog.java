package com.wherecar.batch.main.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name="gps_logs")
@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GpsLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="gps_log_id")
    private Long id;

    private String mdn;

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
