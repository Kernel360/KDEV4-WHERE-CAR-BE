package com.wherecar.rest.service;

import com.wherecar.rest.dto.RegisterCarRequest;

public interface CarService {

    void registerCar(RegisterCarRequest registerCarRequest);
    void updateCar(Long id, RegisterCarRequest registerCarRequest);

}
