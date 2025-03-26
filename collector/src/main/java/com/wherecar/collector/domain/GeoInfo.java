package com.wherecar.collector.domain;

import com.wherecar.rest.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name="geo_infos")
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GeoInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="geo_info_id")
    private Long id;

    private String geoEventType;
    private String geoRange;
    private String latitude;
    private String longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
}
