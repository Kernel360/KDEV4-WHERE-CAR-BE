package com.wherecar.collector.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "on_off_logs")
@Entity
@Getter
@Builder
@ToString(exclude = "car")
@NoArgsConstructor
@AllArgsConstructor
public class OnOffLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="on_off_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    @Column(name = "gps_condition")
    private String gpsCondition;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longitude")
    private Integer longitude;

    @Column(name = "angle")
    private Integer angle;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "on_sum")
    private Integer onSum;

    @Column(name = "off_sum")
    private Integer offSum;

    @Column(name = "on_mileage")
    private Integer onMileage;

    @Column(name = "off_mileage")
    private Integer offMileage;

    @Column(name = "on_time")
    private LocalDateTime onTime;

    @Column(name = "off_time")
    private LocalDateTime offTime;

    @Column(name = "driver")
    private String driver;

    @Column(name = "description")
    private String description;

}
