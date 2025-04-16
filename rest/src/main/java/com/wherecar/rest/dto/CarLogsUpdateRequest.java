package com.wherecar.rest.dto;

import com.wherecar.rest.domain.DriveType;
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
