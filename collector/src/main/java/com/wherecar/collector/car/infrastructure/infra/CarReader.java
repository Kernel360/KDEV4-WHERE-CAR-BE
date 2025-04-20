package com.wherecar.collector.car.infrastructure.infra;

import com.wherecar.collector.car.domain.Car;

public interface CarReader {

    Car getCarByMdn(String mdn);
}
