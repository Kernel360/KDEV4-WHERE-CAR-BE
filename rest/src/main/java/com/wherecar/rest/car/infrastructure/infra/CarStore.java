package com.wherecar.rest.car.infrastructure.infra;

import com.wherecar.rest.car.domain.Car;


public interface CarStore {

    Car store(Car car);
    void deleteById(Long id);

}
