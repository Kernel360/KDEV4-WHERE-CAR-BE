package com.wherecar.collector.geolog.domain;

import com.wherecar.collector.car.domain.Car;
import com.wherecar.collector.common.domain.BaseEntity;
import com.wherecar.collector.geoinfo.domain.GeoInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;


@Table(name="geo_logs")
@Entity
@Builder
@ToString(exclude = {"car", "geoInfo"})
@NoArgsConstructor
@AllArgsConstructor
public class GeoLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="geo_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    @Column(name = "o_time")
    private LocalDateTime oTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="geo_info_id")
    private GeoInfo geoInfo;

    @Column(name = "evaluate_value")
    private String evaluateValue;

}
