package com.wherecar.collector.carlog.infrastructure.infra;

import com.wherecar.collector.carlog.domain.CarLog;

import java.util.Optional;

public interface CarLogReader {

    Optional<CarLog> findPreviousOffLogByMdn(String mdn);

    CarLog getPreviousOnLogByMdn(String mdn);
}
