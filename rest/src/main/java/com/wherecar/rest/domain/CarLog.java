package com.wherecar.rest.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "car_logs")
@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarLog extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_log_id")
    private Long id;

    //car
    private String mdn;

    @Enumerated(EnumType.STRING)
    private GpsConditionType onGpsCondition;

    private Double onLatitude;
    private Double onLongitude;
    private Integer onAngle;
    private Integer onSpeed;
    private Integer onSum;
    private Integer onMileage;
    private LocalDateTime onTime;

    @Enumerated(EnumType.STRING)
    private GpsConditionType offGpsCondition;

    private Double offLatitude;
    private Double offLongitude;
    private Integer offAngle;
    private Integer offSpeed;
    private Integer offSum;
    private Integer offMileage;
    private LocalDateTime offTime;

    private String driver;
    private String description;

    @Enumerated(EnumType.STRING)
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
