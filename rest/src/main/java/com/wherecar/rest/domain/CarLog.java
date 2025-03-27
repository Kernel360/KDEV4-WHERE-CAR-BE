package com.wherecar.rest.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "car_logs")
@Entity
@Builder
@ToString(exclude = "car")
@NoArgsConstructor
@AllArgsConstructor
public class CarLog extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="car_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    private String onGpsCondition;
    private Double onLatitude;
    private Double onLongitude;
    private Double onAngle;
    private Double onSpeed;
    private Double onSum;
    private Double onMileage;
    private LocalDateTime onTime;

    private String offGpsCondition;
    private Double offLatitude;
    private Double offLongitude;
    private Double offAngle;
    private Double offSpeed;
    private Double offSum;
    private Double offMileage;
    private LocalDateTime offTime;

    private String driver;
    private String description;

    @Enumerated(EnumType.STRING)
    private DriveType driveType;
}
