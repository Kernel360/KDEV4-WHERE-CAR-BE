package com.wherecar.collector.domain;

import com.wherecar.collector.domain.constant.DriveType;
import com.wherecar.collector.domain.constant.GpsConditionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

@Table(name = "car_logs")
@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CarLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_log_id")
    private Long id;

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

    public static boolean isSameOffSum(Integer previousOffSum, String currentOnSum) {
        return Objects.equals(previousOffSum, Integer.parseInt(currentOnSum));
    }

    public static Double parseLatLon(String latLon) {
        return (double) Integer.parseInt(latLon) / 1000000;
    }

    public static LocalDateTime parseOnOffTime(String onOffTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(onOffTime, formatter);
    }

    public static Integer getSumToAdd(Integer onSum, Integer offSum) {
        Integer sumToAdd = 0;

        if (onSum <= offSum) {
            sumToAdd = offSum - onSum;
        }
        if (onSum > offSum) {   // 주행 거리가 10,000km(10,000,000m)를 넘었을 경우
            sumToAdd = (offSum + 10000000) - onSum;
        }
        return sumToAdd;
    }

    public static GpsConditionType getGpsConditionType(String gcd) {
        if (Arrays.stream(GpsConditionType.values()).noneMatch(e -> e.name().equals(gcd))) {
            throw new RuntimeException("잘못된 값입니다. 유효한 값은 A, V, O입니다.");
        }
        return GpsConditionType.valueOf(gcd);
    }

    public static Double getOffMileage(Double onMileage, Integer sumToAdd) {
        return onMileage + (double) sumToAdd / 1000;
    }

}
