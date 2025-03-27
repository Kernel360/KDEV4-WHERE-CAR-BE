package com.wherecar.rest.service;

import com.wherecar.rest.domain.Car;
import com.wherecar.rest.dto.RegisterCarRequest;
import com.wherecar.rest.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
//    TODO: company 정보가 추가되면, companyId를 이용해 조회 후 설정할 것
//    private final CompanyRepository companyRepository;

    @Override
    public void registerCar(RegisterCarRequest registerCarRequest) {

//        Company company = companyRepository.findById(registerCarRequest.getCompanyId())
//                .orElseThrow(() -> new RuntimeException("회사 정보가 존재하지 않습니다."));

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
    public void updateCar(Long id, RegisterCarRequest registerCarRequest) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("차량을 찾을 수 없습니다."));

        car.setMake(registerCarRequest.getMake());
        car.setModel(registerCarRequest.getModel());
        car.setYear(registerCarRequest.getYear());
        car.setMileage(registerCarRequest.getMileage());
        car.setMdn(registerCarRequest.getMdn());
        car.setOwnerType(registerCarRequest.getOwnerType());
        car.setAcquisitionType(registerCarRequest.getAcquisitionType());
        car.setBatteryVoltage(registerCarRequest.getBatteryVoltage());

        carRepository.save(car);
    }
}
