package com.wherecar.rest.user.dto;

import com.wherecar.rest.dto.CompanyRequest;
import lombok.Data;

@Data
public class RootUserRequest {
    private UserRequest user;
    private CompanyRequest company;
}
