package com.wherecar.collector.service;

import com.wherecar.collector.domain.CarLog;
import com.wherecar.collector.domain.CarStatus;
import com.wherecar.collector.repository.CarLogRepository;
import com.wherecar.collector.repository.CarStatusRepository;
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
    private final CarStatusRepository carStatusRepository;

    @Override
    public void saveCarLog(CarLog carLog, CarStatus carStatus) {
        carLogRepository.save(carLog);
        carStatusRepository.save(carStatus);    // mileage 최신화 후 자동차 상태 저장
    }
}
