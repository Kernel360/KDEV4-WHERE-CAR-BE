package com.wherecar.rest.carlogsummary.domain;


import com.wherecar.rest.carlog.domain.constant.DriveType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "car_log_summaries")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_log_summary_id")
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    private String mdn;
    //    (km)
    private Integer distance;
    //    (km/h)
    private Integer maxSpeed;
    private Integer averageSpeed;

    @Enumerated(EnumType.STRING)
    private DriveType driveType;

    private LocalDateTime onTime;
    private Double onLatitude;
    private Double onLongitude;

    private LocalDateTime offTime;
    private Double offLatitude;
    private Double offLongitude;






}