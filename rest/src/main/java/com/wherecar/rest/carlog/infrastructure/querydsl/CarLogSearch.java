package com.wherecar.rest.carlog.infrastructure.querydsl;

import com.wherecar.rest.carlog.domain.CarLog;
import com.wherecar.rest.carlog.domain.constant.DriveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CarLogSearch {
    Page<CarLog> searchCarLogWithFilter(Long companyId, String mdn, LocalDateTime startTime, LocalDateTime endTime, DriveType driveType, Pageable pageable);
}
