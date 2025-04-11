package com.wherecar.rest.service;

import com.wherecar.rest.dto.CarOverviewResponse;
import com.wherecar.rest.dto.CarResponse;
import com.wherecar.rest.dto.CarRegisterRequest;

import java.util.List;

public interface CarService {

    void createCar(Long companyId, CarRegisterRequest carRegisterRequest);
    void updateCar(Long id, CarRegisterRequest carRegisterRequest);
    void deleteCar(Long id);
    List<CarResponse> getAllCars(Long companyId, int page, int size);
    CarResponse getCarDetails(Long id);
    CarOverviewResponse getCarOverview(Long companyId);
    List<CarResponse> gatCarsByStatus(Long companyId);
}
