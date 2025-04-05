package com.wherecar.rest.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="geo_logs")
@Entity
@Getter
@Builder
@ToString(exclude = "geoInfo")
@NoArgsConstructor
@AllArgsConstructor
public class GeoLog extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="geo_log_id")
    private Long id;

    private String mdn;


    @Column(name = "o_time")
    private LocalDateTime oTime;

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

    @Column(name = "sum")
    private Integer sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="geo_info_id")
    private GeoInfo geoInfo;

    @Column(name = "evaluate_value")
    private String evaluateValue;

    public void changeAngle(Integer angle) {
        this.angle = angle;
    }

    public void changeEvaluate_value(String evaluateValue) {
        this.evaluateValue = evaluateValue;
    }

    public void changeGpsCondition(String gpsCondition) {
        this.gpsCondition = gpsCondition;
    }

    public void changeLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public void changeLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public void changeSpeed(Integer speed) {
        this.speed = speed;
    }

    public void changeSum(Integer sum) {
        this.sum = sum;
    }

    public void changeMdn(String mdn) {
        this.mdn = mdn;
    }

}
