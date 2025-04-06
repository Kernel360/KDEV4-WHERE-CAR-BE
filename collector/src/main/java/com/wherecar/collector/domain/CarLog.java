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

    // car
    @Column(name = "mdn")
    private String mdn;

    @Enumerated(EnumType.STRING)
    @Column(name = "on_gps_condition")
    private GpsConditionType onGpsCondition;

    @Column(name = "on_latitude")
    private Double onLatitude;

    @Column(name = "on_longitude")
    private Double onLongitude;

    @Column(name = "on_angle")
    private Integer onAngle;

    @Column(name = "on_speed")
    private Integer onSpeed;

    @Column(name = "on_sum")
    private Integer onSum;

    @Column(name = "on_mileage")
    private Double onMileage;

    @Column(name = "on_time")
    private LocalDateTime onTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "off_gps_condition")
    private GpsConditionType offGpsCondition;

    @Column(name = "off_latitude")
    private Double offLatitude;

    @Column(name = "off_longitude")
    private Double offLongitude;

    @Column(name = "off_angle")
    private Integer offAngle;

    @Column(name = "off_speed")
    private Integer offSpeed;

    @Column(name = "off_sum")
    private Integer offSum;

    @Column(name = "off_mileage")
    private Double offMileage;

    @Column(name = "off_time")
    private LocalDateTime offTime;

    @Column(name = "driver")
    private String driver;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "drive_type")
    private DriveType driveType;

    public void changeDriver(String driver) {
        this.driver = driver;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeDriveType(DriveType driveType) {
        this.driveType = driveType;
    }
}
