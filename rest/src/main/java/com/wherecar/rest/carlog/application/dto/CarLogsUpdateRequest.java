package com.wherecar.rest.carlog.application.dto;

import com.wherecar.rest.carlog.domain.constant.DriveType;
import lombok.*;

@Setter
@Getter
@ToString
public class CarLogsUpdateRequest {

    private String driver;
    private String description;
    private DriveType driveType;

}
