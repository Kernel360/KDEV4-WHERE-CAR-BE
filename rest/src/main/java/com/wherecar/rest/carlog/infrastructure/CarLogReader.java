package com.wherecar.rest.carlog.infrastructure;

import com.wherecar.rest.carlog.domain.CarLog;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface CarLogReader {

    Page<CarLog> getCarLogsFiltered(
            Long companyId,
            String mdn,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int page,
            int size
    );

    CarLog getCarLogById(Long carLogId);

}
