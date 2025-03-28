package com.wherecar.rest.service;

import com.wherecar.rest.dto.CarLogsResponse;
import com.wherecar.rest.dto.CarRegisterRequest;
import com.wherecar.rest.dto.CarResponse;

import java.util.List;

public interface CarLogService {
    List<CarLogsResponse> getCarLogs(int page, int size);

    //Todo: 차량 상세 운행일지 정보 조회 getCarLogDetails()

    //Todo: 상세 운행일지 수정 updateCarLogDetails()
}
