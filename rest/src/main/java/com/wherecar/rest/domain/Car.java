package com.wherecar.rest.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "cars")
@Entity
@Getter
@Builder
@ToString(exclude = {"company", "geoInfo","carStatus"})
@NoArgsConstructor
@AllArgsConstructor
public class Car extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_id")
    private long id;

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
