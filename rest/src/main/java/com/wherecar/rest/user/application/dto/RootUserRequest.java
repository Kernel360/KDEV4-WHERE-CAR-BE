package com.wherecar.rest.user.application.dto;

import com.wherecar.rest.company.application.dto.CompanyRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RootUserRequest {
    private UserRequest user;
    private CompanyRequest company;
}
