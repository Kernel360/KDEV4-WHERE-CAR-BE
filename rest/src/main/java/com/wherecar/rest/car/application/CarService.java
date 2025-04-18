package com.wherecar.rest.car.application;

import com.wherecar.rest.car.application.dto.CarOverviewResponse;
import com.wherecar.rest.car.application.dto.CarResponse;
import com.wherecar.rest.car.application.dto.CarRegisterRequest;

import java.util.List;

public interface CarService {

    CarResponse createCar(Long companyId, CarRegisterRequest carRegisterRequest);
    CarResponse updateCar(Long id, CarRegisterRequest carRegisterRequest);
    void deleteCar(Long id);
    List<CarResponse> getAllCars(Long companyId, int page, int size);
    CarResponse getCarDetails(Long id);
    CarOverviewResponse getCarOverview(Long companyId);
    List<CarResponse> gatCarsByStatus(Long companyId);

}
