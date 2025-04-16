package com.wherecar.rest.carlog.application;

import com.wherecar.rest.carlog.application.dto.CarLogDetailResponse;
import com.wherecar.rest.carlog.application.dto.CarLogsResponse;
import com.wherecar.rest.carlog.application.dto.CarLogsUpdateRequest;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface CarLogService {

    Page<CarLogsResponse> getCarLogsFiltered(Long companyId, String mdn, LocalDateTime startTime, LocalDateTime endTime, int page, int size);

    CarLogDetailResponse getCarLogsDetails(Long logId);

    void updateCarLogDetails(Long id, CarLogsUpdateRequest carLogsUpdateRequest);

    void deleteCarLogDetails(Long id);

    CarLogsResponse getAllCarLogsStatics(Long companyId);

}
