package com.wherecar.batch.main.domain;

import com.wherecar.batch.main.domain.constant.AcquisitionType;
import com.wherecar.batch.main.domain.constant.OwnerType;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "cars")
@Entity
@Getter
@Builder
@ToString(exclude = {"company", "geoInfo","carStatus"})
@NoArgsConstructor
@AllArgsConstructor
public class Car extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_id")
    private long id;


    // Todo: make Enum 만들자
    private String make;
    private String model;
    private String year;
    private String mdn;

    @Enumerated(EnumType.STRING)
    @Column(name="owner_type")
    private OwnerType ownerType;

    @Enumerated(EnumType.STRING)
    @Column(name="acquisition_type")
    private AcquisitionType acquisitionType;


    @Column(name="company_id")
    private Long companyId;


    @Column(name="geo_info_id")
    private Long geoInfoId;


}
