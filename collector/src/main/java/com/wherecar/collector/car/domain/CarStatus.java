package com.wherecar.collector.car.domain;

import com.wherecar.collector.common.constant.CarState;
import com.wherecar.collector.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "carStatuses")
@Entity
@Getter
@Builder
@ToString(exclude = "car")
@NoArgsConstructor
@AllArgsConstructor
public class CarStatus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_status_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    @Column(name="mileage")
    private Double mileage;

    @Column(name="battery_voltage")
    private Integer batteryVoltage;

    @Enumerated(EnumType.STRING)
    @Column(name="car_state")
    private CarState carState;

    public void changeBatteryVoltage(Integer batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public void changeMileage(Double mileage) {
        this.mileage = mileage;
    }

    public void changeCarState(CarState carState) {
        this.carState = carState;
    }

}

