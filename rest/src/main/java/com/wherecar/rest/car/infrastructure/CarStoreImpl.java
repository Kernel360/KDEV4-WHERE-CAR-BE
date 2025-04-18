package com.wherecar.rest.car.infrastructure;

import com.wherecar.rest.car.domain.Car;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarStoreImpl implements CarStore{

    private final CarRepository carRepository;
    private final CarStatusRepository carStatusRepository;

    @Override
    public Car store(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void delete(Long carId) {
        carRepository.deleteById(carId);
    }
}
