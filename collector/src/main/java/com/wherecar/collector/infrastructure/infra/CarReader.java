package com.wherecar.collector.infrastructure.infra;

import com.wherecar.collector.domain.Car;

public interface CarReader {

    Car getCarByMdn(String mdn);
}
