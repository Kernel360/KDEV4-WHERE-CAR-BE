package com.wherecar.collector.service;

import com.wherecar.collector.domain.CarLog;
import com.wherecar.collector.repository.CarLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CarLogSaveServiceImpl implements CarLogSaveService {

    private final CarLogRepository carLogRepository;

    @Override
    public void saveCarLog(CarLog carLog) {
        carLogRepository.save(carLog);
    }
}
