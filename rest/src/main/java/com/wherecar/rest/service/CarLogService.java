package com.wherecar.rest.service;

import com.wherecar.rest.dto.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface CarLogService {

    Page<CarLogsResponse> getCarLogsFiltered(Long companyId, String mdn, LocalDateTime startTime, LocalDateTime endTime, int page, int size);

    CarLogDetailResponse getCarLogsDetails(Long logId);

    void updateCarLogDetails(Long id, CarLogsUpdateRequest carLogsUpdateRequest);

    void deleteCarLogDetails(Long id);

}
