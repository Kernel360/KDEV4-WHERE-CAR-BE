package com.wherecar.rest.service;

import com.wherecar.rest.domain.Car;
import com.wherecar.rest.dto.CarResponse;
import com.wherecar.rest.dto.CarRegisterRequest;
import com.wherecar.rest.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
//    TODO: company 정보가 추가되면, companyId를 이용해 조회 후 설정할 것
//    private final CompanyRepository companyRepository;

    @Override
    public void registerCar(CarRegisterRequest registerCarRequest) {

//        Company company = companyRepository.findById(registerCarRequest.getCompanyId())
//                .orElseThrow(() -> new RuntimeException("Company 정보가 존재하지 않습니다."));

        Car car = Car.builder()
                .make(registerCarRequest.getMake())
                .model(registerCarRequest.getModel())
                .year(registerCarRequest.getYear())
                .mileage(registerCarRequest.getMileage())
                .mdn(registerCarRequest.getMdn())
                .ownerType(registerCarRequest.getOwnerType())
                .acquisitionType(registerCarRequest.getAcquisitionType())
                .batteryVoltage(registerCarRequest.getBatteryVoltage())
//                .company(company)
                .build();

        carRepository.save(car);
    }

    @Override
    public void updateCar(Long id, CarRegisterRequest registerCarRequest) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("차량을 찾을 수 없습니다."));

        Car updatedCar = Car.builder()
                .id(car.getId())
                .make(registerCarRequest.getMake())
                .model(registerCarRequest.getModel())
                .year(registerCarRequest.getYear())
                .mileage(registerCarRequest.getMileage())
                .mdn(registerCarRequest.getMdn())
                .ownerType(registerCarRequest.getOwnerType())
                .acquisitionType(registerCarRequest.getAcquisitionType())
                .batteryVoltage(registerCarRequest.getBatteryVoltage())
                .company(car.getCompany())
                .build();

        carRepository.save(updatedCar);
    }

    @Override
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("차량을 찾을 수 없습니다.");
        }
        carRepository.deleteById(id);
    }

    @Override
    public List<CarResponse> getAllCars(int page, int size) {
        // Todo: 현재 사용자 정보 조회 (Admin, User에 따라 구분)
        // Todo: 현재 사용자의 Company 아이디 조회

        Long userCompanyId = null;

        PageRequest pageRequest = PageRequest.of(page,size);

        Page<Car> carPage = carRepository.findByCompanyId(userCompanyId, pageRequest);

        return carPage.stream().map(car -> CarResponse.builder()
                        .id(car.getId())
                        .mdn(car.getMdn())
                        .make(car.getMake())
                        .model(car.getModel())
                        .year(car.getYear())
                        .mileage(car.getMileage())
                        .ownerType(car.getOwnerType())
                        .acquisitionType(car.getAcquisitionType())

//                      Todo: Company 추가되면 반영, CompanyName을 추가할 건지 결정
//                      .companyName(car.getCompany() != null ? car.getCompany().getName() : null)

                        .batteryVoltage(car.getBatteryVoltage())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CarResponse getCarDetails(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("차량을 찾을 수 없습니다."));

        return CarResponse.builder()
                .id(car.getId())
                .mdn(car.getMdn())
                .make(car.getMake())
                .model(car.getModel())
                .year(car.getYear())
                .mileage(car.getMileage())
                .ownerType(car.getOwnerType())
                .acquisitionType(car.getAcquisitionType())

//                Todo: Company 추가되면 반영
//                .companyName(car.getCompany() != null ? car.getCompany().getName() : null)

                .batteryVoltage(car.getBatteryVoltage())
                .build();
    }

}
