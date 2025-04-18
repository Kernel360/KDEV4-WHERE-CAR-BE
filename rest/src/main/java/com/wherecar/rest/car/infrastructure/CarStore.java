package com.wherecar.rest.car.infrastructure;

import com.wherecar.rest.car.domain.Car;


public interface CarStore {

    Car store(Car car);
    void delete(Long carId);

}
