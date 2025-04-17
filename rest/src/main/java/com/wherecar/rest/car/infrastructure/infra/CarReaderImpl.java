package com.wherecar.rest.car.infrastructure.infra;

import com.wherecar.rest.car.application.dto.CarOverviewResponse;
import com.wherecar.rest.car.domain.Car;
import com.wherecar.rest.car.domain.CarFactory;
import com.wherecar.rest.car.domain.constant.CarState;
import com.wherecar.rest.car.domain.constant.OwnerType;
import com.wherecar.rest.car.infrastructure.CarRepository;
import com.wherecar.rest.car.infrastructure.CarStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarReaderImpl implements CarReader{

    private final CarRepository carRepository;
    private final CarFactory carFactory;
    private final CarStatusRepository carStatusRepository;

    @Override
    public Car getCarById(Long id) {
        return carRepository.findCarWithStatus(id).orElseThrow(() -> new RuntimeException("차량을 찾을 수 없습니다."));
    }

    @Override
    public Page<Car> getCarsById(Long companyId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return carRepository.findByCompanyIdWithCarStatus(companyId, pageRequest);
    }

    public CarOverviewResponse getCarOverviewByCompanyId(Long companyId) {
        long totalCars = carRepository.countByCompanyId(companyId);
        long totalCorporateCars = carRepository.countByCompanyIdAndOwnerType(companyId, OwnerType.CORPORATE);
        long totalPrivateCars = carRepository.countByCompanyIdAndOwnerType(companyId, OwnerType.PERSONAL);
        long activeCars = carStatusRepository.countByCompanyIdAndCarState(companyId, CarState.RUNNING);
        long inactiveCars = carStatusRepository.countByCompanyIdAndCarState(companyId, CarState.STOPPED);
        long untrackedCars = carStatusRepository.countByCompanyIdAndCarState(companyId, CarState.NOT_REGISTERED);

        return CarOverviewResponse.builder()
                .totalCars(totalCars)
                .totalCorporateCars(totalCorporateCars)
                .totalPrivateCars(totalPrivateCars)
                .activeCars(activeCars)
                .inactiveCars(inactiveCars)
                .untrackedCars(untrackedCars)
                .build();
    }


}
