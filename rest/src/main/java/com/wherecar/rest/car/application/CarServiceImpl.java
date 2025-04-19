package com.wherecar.rest.car.application;

import com.wherecar.rest.car.domain.Car;
import com.wherecar.rest.car.domain.CarFactory;
import com.wherecar.rest.car.infrastructure.CarReader;
import com.wherecar.rest.car.infrastructure.CarStore;
import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.car.application.dto.CarOverviewResponse;
import com.wherecar.rest.car.application.dto.CarResponse;
import com.wherecar.rest.car.application.dto.CarRegisterRequest;
import com.wherecar.rest.car.infrastructure.CarRepository;
import com.wherecar.rest.company.infrastructure.CompanyReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarReader carReader;
    private final CompanyReader companyReader;
    private final CarFactory carFactory;

    private final CarStore carStore;

    @Override
    public CarResponse createCar(Long companyId, CarRegisterRequest carRegisterRequest) {

        Company company = companyReader.getCompanyById(companyId);
        Car car = carFactory.toCar(carRegisterRequest, company);
        car = carStore.store(car);
        return carFactory.toCarResponse(car);

    }

    //Todo: geoInfo 수정 기능 추가
    @Override
    public CarResponse updateCar(Long id, CarRegisterRequest carRegisterRequest) {

        Car car = carReader.getCarById(id);
        car.updateCar(carRegisterRequest);
        car = carStore.store(car);

        return carFactory.toCarResponse(car);
    }

    @Override
    public void deleteCar(Long id) {

        carStore.delete(id);

    }

    //Todo: 반환값 수정 Page (프론트엔드도 같이 수정)
    @Override
    @Transactional(readOnly = true)
    public List<CarResponse> getAllCars(Long companyId, int page, int size) {

        Page<Car> carPage = carReader.getCarsById(companyId, page, size);

        return carFactory.toCarResponseList(carPage.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponse getCarDetails(Long id) {
        Car car = carReader.getCarById(id);

        return carFactory.toCarResponse(car);
    }

    @Override
    public CarOverviewResponse getCarOverview(Long companyId) {
        return carReader.getCarOverviewByCompanyId(companyId);
    }

    //Todo: 추후 리팩토링 관제 이외의 상태별로 조회할 수 있도록 수정
    @Override
    public List<CarResponse> gatCarsByStatus(Long companyId) {
        List<Car> cars = carRepository.findByCompanyIdWithRegisteredCarStatus(companyId);
        return cars.stream()
                .map(car -> CarResponse.builder()
                        .id(car.getId())
                        .mdn(car.getMdn())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
