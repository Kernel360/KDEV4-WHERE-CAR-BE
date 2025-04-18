package com.wherecar.collector.infrastructure.infra;

import com.wherecar.collector.domain.Car;
import com.wherecar.collector.infrastructure.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarReaderImpl implements CarReader {

    private final CarRepository carRepository;

    @Override
    public Car getCarByMdn(String mdn) {
        return carRepository.findByMdn(mdn).orElseThrow(() -> new RuntimeException("존재하지 않는 차입니다."));
    }
}
