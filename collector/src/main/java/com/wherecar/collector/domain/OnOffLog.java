package com.wherecar.collector.domain;

import com.wherecar.rest.domain.BaseEntity;
import com.wherecar.rest.domain.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "on_off_logs")
@Entity
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

    private String gpsCondition;
    private Double latitude;
    private Double longitude;
    private Double angle;
    private Double speed;

    private Double onSum;
    private Double offSum;

    private Double onMileage;
    private Double offMileage;

    private LocalDateTime onTime;
    private LocalDateTime offTime;

    private String driver;
    private String description;
}
