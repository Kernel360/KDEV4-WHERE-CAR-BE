package com.wherecar.collector.carlog.infrastructure;

import com.wherecar.collector.carlog.domain.CarLog;

import java.util.Optional;

public interface CarLogReader {

    Optional<CarLog> findPreviousOffLogByMdn(String mdn);

    CarLog getPreviousOnLogByMdn(String mdn);
}
