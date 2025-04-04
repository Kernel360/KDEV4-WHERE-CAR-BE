package com.wherecar.collector.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Table(name="gps_logs")
@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GpsLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="gps_log_id")
    private Long id;

    @Column(name = "mdn")
    private String mdn;

    // oTime + sec
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "gps_condition")
    private GpsConditionType gpsCondition;

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
