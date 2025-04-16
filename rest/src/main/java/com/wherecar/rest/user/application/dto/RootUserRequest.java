package com.wherecar.rest.user.application.dto;

import com.wherecar.rest.company.application.dto.CompanyRequest;
import lombok.Data;

@Data
public class RootUserRequest {
    private UserRequest user;
    private CompanyRequest company;
}
