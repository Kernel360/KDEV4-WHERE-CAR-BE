package com.wherecar.rest.service;

import com.wherecar.rest.dto.CarResponse;
import com.wherecar.rest.dto.RegisterCarRequest;

import java.util.List;

public interface CarService {

    void registerCar(RegisterCarRequest registerCarRequest);
    void updateCar(Long id, RegisterCarRequest registerCarRequest);
    void deleteCar(Long id);
    List<CarResponse> getAllCars(int page, int size);
}
