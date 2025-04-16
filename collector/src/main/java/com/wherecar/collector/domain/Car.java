package com.wherecar.collector.domain;

import com.wherecar.collector.domain.constant.AcquisitionType;
import com.wherecar.collector.domain.constant.OwnerType;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "cars")
@Entity
@Getter
@Builder
@ToString(exclude = {"company", "geoInfo", "carStatus"})
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
    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private String year;

    @Column(name = "mdn")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="geo_info_id")
    private GeoInfo geoInfo;

    public void changeMake(String make) {
        this.make = make;
    }

    public void changeModel(String model) {
        this.model = model;
    }

    public void changeYear(String year) {
        this.year = year;
    }

    public void changeMdn(String mdn) {
        this.mdn = mdn;
    }

    public void changeOwnerType(OwnerType ownerType) {
        this.ownerType = ownerType;
    }

    public void changeAcquisitionType(AcquisitionType acquisitionType) {
        this.acquisitionType = acquisitionType;
    }

    public void changeGeoInfo(GeoInfo geoInfo) {
        this.geoInfo = geoInfo;
    }
}
