package com.wherecar.rest.service;

import com.wherecar.rest.domain.Car;
import com.wherecar.rest.domain.CarState;
import com.wherecar.rest.domain.CarStatus;
import com.wherecar.rest.domain.Company;
import com.wherecar.rest.dto.CarResponse;
import com.wherecar.rest.dto.CarRegisterRequest;
import com.wherecar.rest.repository.CarRepository;
import com.wherecar.rest.repository.CarStatusRepository;
import com.wherecar.rest.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final CompanyRepository companyRepository;

    @Override
    public void createCar(Long companyId, CarRegisterRequest carRegisterRequest) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company 정보가 존재하지 않습니다."));

        Car car = Car.builder()
                .make(carRegisterRequest.getMake())
                .model(carRegisterRequest.getModel())
                .year(carRegisterRequest.getYear())
                .mdn(carRegisterRequest.getMdn())
                .ownerType(carRegisterRequest.getOwnerType())
                .acquisitionType(carRegisterRequest.getAcquisitionType())
                .company(company)
                .build();

        CarStatus carStatus = CarStatus.builder()
                .car(car)
                .carState(CarState.NOT_REGISTERED)
                .mileage(carRegisterRequest.getMileage())
                .batteryVoltage(carRegisterRequest.getBatteryVoltage())
                .build();

        carRepository.save(car);
        carStatusRepository.save(carStatus);

    }

    @Override
    public void updateCar(Long id, CarRegisterRequest carRegisterRequest) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("차량을 찾을 수 없습니다."));

        //carStatus, mileage, batteryVoltage 수정 불가
        //Todo: geoInfo 수정 기능 추가
        car.changeMake(carRegisterRequest.getMake());
        car.changeModel(carRegisterRequest.getModel());
        car.changeYear(carRegisterRequest.getYear());
        car.changeMdn(carRegisterRequest.getMdn());
        car.changeOwnerType(carRegisterRequest.getOwnerType());
        car.changeAcquisitionType(carRegisterRequest.getAcquisitionType());

        carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("차량을 찾을 수 없습니다.");
        }
        carRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponse> getAllCars(int page, int size) {

        Long userCompanyId = null;

        PageRequest pageRequest = PageRequest.of(page,size);

        Page<Car> carPage = carRepository.findByCompanyIdWithCarStatus(userCompanyId, pageRequest);

        //TODO: 필요한 데이터만 남기기
        return carPage.stream().map(car -> CarResponse.builder()
                        .id(car.getId())
                        .mdn(car.getMdn())
                        .make(car.getMake())
                        .ownerType(car.getOwnerType())
                        .acquisitionType(car.getAcquisitionType())
                        .carState(car.getCarStatus().getCarState())
                        .batteryVoltage(car.getCarStatus().getBatteryVoltage())
                        .mileage(car.getCarStatus().getMileage())
                        .companyName(car.getCompany() != null ? car.getCompany().getName() : null)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponse getCarDetails(Long id) {
        Car car = carRepository.findCarWithStatus(id).orElseThrow(() -> new RuntimeException("차량을 찾을 수 없습니다."));

        return CarResponse.builder()
                .id(car.getId())
                .mdn(car.getMdn())
                .make(car.getMake())
                .model(car.getModel())
                .year(car.getYear())
                .ownerType(car.getOwnerType())
                .acquisitionType(car.getAcquisitionType())
                .mileage(car.getCarStatus().getMileage())
                .batteryVoltage(car.getCarStatus().getBatteryVoltage())
                .carState(car.getCarStatus().getCarState())
                .companyName(car.getCompany() != null ? car.getCompany().getName() : null)
                .build();
    }

}
