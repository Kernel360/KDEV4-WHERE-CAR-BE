package com.wherecar.rest.car.infrastructure.infra;

import com.wherecar.rest.car.application.dto.CarOverviewResponse;
import com.wherecar.rest.car.domain.Car;
import org.springframework.data.domain.Page;

public interface CarReader {

    Car getCarById(Long id);

    Page<Car> getCarsById(Long companyId, int page, int size);

    CarOverviewResponse getCarOverviewByCompanyId(Long companyId);

}
