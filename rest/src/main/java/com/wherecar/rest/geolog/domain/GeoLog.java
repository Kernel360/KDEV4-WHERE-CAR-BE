package com.wherecar.rest.geolog.domain;

import com.wherecar.rest.common.domain.BaseEntity;
import com.wherecar.rest.geoinfo.domain.GeoInfo;
import com.wherecar.rest.geolog.application.dto.GeoLogRequest;
import com.wherecar.rest.gpslog.domain.constant.GpsConditionType;
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
public class GeoLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="geo_log_id")
    private Long id;

    @Column(name = "mdn")
    private String mdn;

    @Column(name = "o_time")
    private LocalDateTime oTime;

    @Column(name = "gps_condition")
    @Enumerated(EnumType.STRING)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="geo_info_id")
    private GeoInfo geoInfo;

    @Column(name = "evaluate_value")
    private String evaluateValue;

    public void updateGeoLog(GeoLogRequest geoLogRequest){
        this.angle = geoLogRequest.getAngle();
        this.gpsCondition = geoLogRequest.getGpsCondition();
        this.latitude = geoLogRequest.getLatitude();
        this.longitude = geoLogRequest.getLongitude();
        this.speed = geoLogRequest.getSpeed();
        this.sum = geoLogRequest.getSum();
        this.mdn = geoLogRequest.getMdn();
    }

}
