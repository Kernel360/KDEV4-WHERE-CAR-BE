package com.wherecar.rest.car.domain;

import com.wherecar.rest.car.application.dto.CarRegisterRequest;
import com.wherecar.rest.car.domain.constant.AcquisitionType;
import com.wherecar.rest.car.domain.constant.OwnerType;
import com.wherecar.rest.common.domain.BaseEntity;
import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.geoinfo.domain.GeoInfo;
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
    private Long id;

    @OneToOne(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CarStatus carStatus;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="geo_info_id")
    private GeoInfo geoInfo;

    public void updateCar(CarRegisterRequest carRegisterRequest) {
        this.make = carRegisterRequest.getMake();
        this.model = carRegisterRequest.getModel();
        this.year = carRegisterRequest.getYear();
        this.mdn = carRegisterRequest.getMdn();
        this.ownerType = carRegisterRequest.getOwnerType();
        this.acquisitionType = carRegisterRequest.getAcquisitionType();
    }

}
