package com.wherecar.rest.carlog.application.dto;

import com.wherecar.rest.carlog.domain.DriveType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarLogsUpdateRequest {

    private String driver;
    private String description;
    private DriveType driveType;

}
