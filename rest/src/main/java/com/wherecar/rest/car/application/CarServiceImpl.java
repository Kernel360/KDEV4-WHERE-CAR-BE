package com.wherecar.rest.car.application;

import com.wherecar.rest.car.domain.Car;
import com.wherecar.rest.car.domain.CarFactory;
import com.wherecar.rest.car.infrastructure.infra.CarReader;
import com.wherecar.rest.car.infrastructure.infra.CarStore;
import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.car.application.dto.CarOverviewResponse;
import com.wherecar.rest.car.application.dto.CarResponse;
import com.wherecar.rest.car.application.dto.CarRegisterRequest;
import com.wherecar.rest.car.infrastructure.CarRepository;
import com.wherecar.rest.car.infrastructure.CarStatusRepository;
import com.wherecar.rest.company.infrastructure.CompanyRepository;
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
    private final CarStatusRepository carStatusRepository;
    private final CarReader carReader;
    private final CompanyRepository companyRepository;
    private final CarFactory carFactory;

    private final CarStore carStore;

    @Override
    public CarResponse createCar(Long companyId, CarRegisterRequest carRegisterRequest) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company 정보가 존재하지 않습니다."));
        Car car = carFactory.toCar(carRegisterRequest, company);

        car = carStore.store(car);


        return carFactory.toCarResponse(car);

    }

    //Todo: geoInfo 수정 기능 추가
    @Override
    public CarResponse updateCar(Long id, CarRegisterRequest carRegisterRequest) {

        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("차량을 찾을 수 없습니다."));
        car.updateCar(carRegisterRequest);
        car = carStore.store(car);

        return carFactory.toCarResponse(car);
    }

    @Override
    public void deleteCar(Long id) {

        carStore.deleteById(id);

    }

    //Todo: 반환값 수정 Page (프론트엔드도 같이 수정)
    @Override
    @Transactional(readOnly = true)
    public List<CarResponse> getAllCars(Long companyId, int page, int size) {

        Page<Car> carPage = carReader.getCarsById(companyId, page, size);

        System.out.println("조회된 차량 리스트: " + carPage.getContent());
        System.out.println("현재 페이지: " + carPage.getNumber());
        System.out.println("총 페이지 수: " + carPage.getTotalPages());
        System.out.println("총 요소 수: " + carPage.getTotalElements());

        return carPage.getContent().stream()
                .map(carFactory::toCarResponse)
                .collect(Collectors.toList());

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

    //todo: 추후 리팩토링 관제 이외의 상태별로 조회할 수 있도록 수정
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
