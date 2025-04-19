package com.wherecar.rest.carlog.infrastructure;

import com.wherecar.rest.carlog.domain.CarLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarLogReaderImpl implements CarLogReader{

    private final CarLogRepository carLogRepository;

    @Override
    public Page<CarLog> getCarLogsFiltered(
            Long companyId,
            String mdn,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return carLogRepository.findCarLogsFiltered(companyId, mdn, startTime, endTime, pageRequest);
    }

    @Override
    public CarLog getCarLogById(Long carLogId) {
        return carLogRepository.findById(carLogId).orElseThrow(() -> new RuntimeException("해당 차량의 일지를 찾을 수 없습니다."));
    }

}
