package com.wherecar.rest.car.domain;

import com.wherecar.rest.car.application.dto.CarRegisterRequest;
import com.wherecar.rest.car.application.dto.CarResponse;
import com.wherecar.rest.car.domain.constant.CarState;
import com.wherecar.rest.company.domain.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CarFactory {

    public Car toCar(CarRegisterRequest carRegisterRequest, Company company) {

        CarStatus carStatus = CarStatus.builder()
                .carState(CarState.NOT_REGISTERED)
                .mileage(carRegisterRequest.getMileage())
                .batteryVoltage(carRegisterRequest.getBatteryVoltage())
                .build();

        Car car = Car.builder()
                .make(carRegisterRequest.getMake())
                .model(carRegisterRequest.getModel())
                .year(carRegisterRequest.getYear())
                .mdn(carRegisterRequest.getMdn())
                .ownerType(carRegisterRequest.getOwnerType())
                .acquisitionType(carRegisterRequest.getAcquisitionType())
                .company(company)
                .carStatus(carStatus)
                .build();

        carStatus.changeCar(car);

        return car;
    }

    public CarResponse toCarResponse(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .mdn(car.getMdn())
                .make(car.getMake())
                .model(car.getModel())
                .year(car.getYear())
                .ownerType(car.getOwnerType())
                .acquisitionType(car.getAcquisitionType())
                .mileage(car.getCarStatus().getMileage())
                .batteryVoltage(car.getCarStatus().getBatteryVoltage())
                .carState(car.getCarStatus().getCarState())
                .companyName(car.getCompany() != null ? car.getCompany().getName() : null)
                .build();
    }

    public List<CarResponse> toCarResponseList(List<Car> cars) {
        return cars.stream()
                .map(this::toCarResponse)
                .collect(Collectors.toList());
    }



}
