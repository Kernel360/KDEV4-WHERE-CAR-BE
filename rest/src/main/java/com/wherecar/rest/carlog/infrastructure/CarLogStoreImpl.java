package com.wherecar.rest.carlog.infrastructure;

import com.wherecar.rest.carlog.domain.CarLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarLogStoreImpl implements CarLogStore{

    private final CarLogRepository carLogRepository;

    @Override
    public CarLog store(CarLog carLog) {
       return carLogRepository.save(carLog);
    }

    @Override
    public void delete(Long carLogId) {
        carLogRepository.deleteById(carLogId);
    }

}
