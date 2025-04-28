package com.wherecar.rest.user.application.dto;

import com.wherecar.rest.company.application.dto.CompanyRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RootUserRequest {

    @Valid
    private UserRequest user;

    @Valid
    private CompanyRequest company;
}
