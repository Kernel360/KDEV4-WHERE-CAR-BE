package com.wherecar.rest.user.application.dto;

import jakarta.validation.Valid;
import lombok.*;

@Setter
@Getter
@ToString
public class SubUserRequest {

    PermissionRequest permission;

    @Valid
    UserRequest user;
}
