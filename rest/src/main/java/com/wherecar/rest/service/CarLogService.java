package com.wherecar.rest.service;

import com.wherecar.rest.dto.CarLogDetailResponse;
import com.wherecar.rest.dto.CarLogsResponse;
import com.wherecar.rest.dto.CarRegisterRequest;
import com.wherecar.rest.dto.CarResponse;

import java.util.List;

public interface CarLogService {
    List<CarLogsResponse> getCarLogs(int page, int size);

    List<CarLogDetailResponse> getCarLogsDetails(Long carId, int page, int size );

    //Todo: 상세 운행일지 수정 updateCarLogDetails()
}
