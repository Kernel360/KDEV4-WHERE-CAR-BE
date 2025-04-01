package com.wherecar.rest.domain;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "carStatuses")
@Entity
@Getter
@Builder
@ToString(exclude = "car")
@NoArgsConstructor
@AllArgsConstructor
public class CarStatus  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="car_status_id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    @Column(name="mileage")
    private Double mileage;

    @Column(name="battery_Voltage")
    private Double batteryVoltage;

    @Enumerated(EnumType.STRING)
    @Column(name="owner_type")
    private CarState carstate;

}
