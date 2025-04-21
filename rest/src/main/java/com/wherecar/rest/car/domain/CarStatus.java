package com.wherecar.rest.car.domain;


import com.wherecar.rest.car.domain.constant.CarState;
import com.wherecar.rest.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "carStatuses")
@Entity
@Getter
@Builder
@ToString(exclude = "car")
@NoArgsConstructor
@AllArgsConstructor
public class CarStatus  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_status_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    @Column(name="mileage")
    private Integer mileage;

    @Column(name="battery_voltage")
    private Integer batteryVoltage;

    @Enumerated(EnumType.STRING)
    @Column(name="car_state")
    private CarState carState;

    public void changeCar(Car car) {
        this.car = car;
    }


}
