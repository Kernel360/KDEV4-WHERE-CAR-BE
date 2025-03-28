package com.wherecar.rest.service;

import com.wherecar.rest.dto.*;

import java.util.List;

public interface CarLogService {

    List<CarLogsResponse> getCarLogs(int page, int size);

    List<CarLogDetailResponse> getCarLogsDetails(Long carId, int page, int size );

    void updateCarLogDetails(Long id, CarLogsUpdateRequest carLogsUpdateRequest);

    void deleteCarLogDetails(Long id);

}
