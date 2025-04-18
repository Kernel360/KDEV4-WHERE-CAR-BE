package com.wherecar.collector.infrastructure.infra;

import com.wherecar.collector.domain.CarLog;

import java.util.Optional;

public interface CarLogReader {

    Optional<CarLog> findPreviousOffLogByMdn(String mdn);

    CarLog getPreviousOnLogByMdn(String mdn);
}
