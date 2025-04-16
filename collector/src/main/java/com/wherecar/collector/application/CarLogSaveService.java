package com.wherecar.collector.application;

import com.wherecar.collector.domain.CarLog;
import com.wherecar.collector.domain.CarStatus;

public interface CarLogSaveService {

    void saveCarLog(CarLog carLog, CarStatus carStatus);
}
