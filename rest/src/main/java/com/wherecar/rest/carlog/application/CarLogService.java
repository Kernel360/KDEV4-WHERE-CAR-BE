package com.wherecar.rest.carlog.application;

import com.wherecar.rest.carlog.application.dto.CarLogResponse;
import com.wherecar.rest.carlog.application.dto.CarLogsUpdateRequest;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface CarLogService {

    Page<CarLogResponse> getCarLogsFiltered(Long companyId, String mdn, LocalDateTime startTime, LocalDateTime endTime, int page, int size);

    CarLogResponse getCarLogDetails(Long carLogId);

    CarLogResponse updateCarLogDetails(Long carLogId, CarLogsUpdateRequest carLogsUpdateRequest);

    void deleteCarLogDetails(Long carLogId);

    CarLogResponse getAllCarLogsStatics(Long companyId);

}
