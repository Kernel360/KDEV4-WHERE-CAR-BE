package com.wherecar.rest.service;

import com.wherecar.rest.dto.*;

import java.util.List;

public interface CarLogService {

    List<CarLogsResponse> getCarLogs(Long companyId, int page, int size);

    List<CarLogsResponse> getCarLogsByCarMdn(Long companyId, String mdn, int page, int size);

    CarLogDetailResponse getCarLogsDetails(Long logId);

    void updateCarLogDetails(Long id, CarLogsUpdateRequest carLogsUpdateRequest);

    void deleteCarLogDetails(Long id);

}
