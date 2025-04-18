package com.wherecar.collector.infrastructure.infra;

import com.wherecar.collector.domain.CarLog;
import com.wherecar.collector.infrastructure.CarLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarLogReaderImpl implements CarLogReader {

    private final CarLogRepository carLogRepository;

    @Override
    public Optional<CarLog> findPreviousOffLogByMdn(String mdn) {
        return carLogRepository.findTopByMdnOrderByOffTimeDesc(mdn);
    }

    @Override
    public CarLog getPreviousOnLogByMdn(String mdn) {
        return carLogRepository.findTopByMdnOrderByOnTimeDesc(mdn).orElseThrow(() -> new RuntimeException("이전 ON 로그가 없습니다."));
    }

}
