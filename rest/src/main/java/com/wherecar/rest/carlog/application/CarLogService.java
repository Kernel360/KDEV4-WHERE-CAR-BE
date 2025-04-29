package com.wherecar.rest.carlog.application;

import com.wherecar.rest.carlog.application.dto.CarLogResponse;
import com.wherecar.rest.carlog.application.dto.CarLogsUpdateRequest;
import com.wherecar.rest.carlog.application.dto.MonthlyMileage;
import com.wherecar.rest.carlog.domain.constant.DriveType;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface CarLogService {

    Page<CarLogResponse> getCarLogsFiltered(Long companyId, String mdn, LocalDateTime startTime, LocalDateTime endTime, DriveType driveType, int page, int size);

    CarLogResponse getCarLogDetails(Long carLogId);

    CarLogResponse updateCarLogDetails(Long carLogId, CarLogsUpdateRequest carLogsUpdateRequest);

    void deleteCarLogDetails(Long carLogId);

    List<MonthlyMileage> getAllCarLogsStatics(Long companyId);

}
