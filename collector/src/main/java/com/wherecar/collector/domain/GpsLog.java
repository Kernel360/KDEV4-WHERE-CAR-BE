package com.wherecar.collector.domain;

import com.wherecar.collector.domain.constant.GpsConditionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Table(name="gps_logs")
@Entity
@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GpsLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static LocalDateTime getTimestamp(String oTime, String sec, DateTimeFormatter timestampFormatter) {
        String timestampString = oTime + sec;     // oTime(yyyyMMddHHmm 형식) + sec(ss 형식) 의 String
        return LocalDateTime.parse(timestampString, timestampFormatter); // String을 LocalDateTime으로 변환
    }

    public static Double parseLatLon(String latLon) {
        return (double) Integer.parseInt(latLon) / 1000000;
    }

    public static GpsConditionType getGpsConditionType(String gcd) {
        if (Arrays.stream(GpsConditionType.values()).noneMatch(e -> e.name().equals(gcd))) {
            throw new RuntimeException("잘못된 값입니다. 유효한 값은 A, V, O입니다.");
        }
        return GpsConditionType.valueOf(gcd);
    }

}
