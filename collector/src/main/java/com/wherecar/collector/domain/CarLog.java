package com.wherecar.collector.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "car_logs")
@Entity
@Getter
@Builder
@ToString(exclude = "car")
@NoArgsConstructor
@AllArgsConstructor
public class CarLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="car_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    @Column(name = "on_gps_condition")
    private String onGpsCondition;

    @Column(name = "on_latitude")
    private Integer onLatitude;

    @Column(name = "on_longitude")
    private Integer onLongitude;

    @Column(name = "on_angle")
    private Integer onAngle;

    @Column(name = "on_speed")
    private Integer onSpeed;

    @Column(name = "on_sum")
    private Integer onSum;

    @Column(name = "on_mileage")
    private Integer onMileage;

    @Column(name = "on_time")
    private LocalDateTime onTime;

    @Column(name = "off_gps_condition")
    private String offGpsCondition;

    @Column(name = "off_latitude")
    private Integer offLatitude;

    @Column(name = "off_longitude")
    private Integer offLongitude;

    @Column(name = "off_angle")
    private Integer offAngle;

    @Column(name = "off_speed")
    private Integer offSpeed;

    @Column(name = "off_sum")
    private Integer offSum;

    @Column(name = "off_mileage")
    private Integer offMileage;

    @Column(name = "off_time")
    private LocalDateTime offTime;

    @Column(name = "driver")
    private String driver;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "drive_type")
    private DriveType driveType;

}
