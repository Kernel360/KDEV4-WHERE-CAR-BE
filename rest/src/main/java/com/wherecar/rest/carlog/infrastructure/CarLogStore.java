package com.wherecar.rest.carlog.infrastructure;

import com.wherecar.rest.carlog.domain.CarLog;

public interface CarLogStore {

    CarLog store(CarLog carLog);
    void delete(Long carLogId);

}
