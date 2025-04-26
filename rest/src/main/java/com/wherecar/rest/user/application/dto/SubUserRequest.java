package com.wherecar.rest.user.application.dto;

import lombok.*;

@Setter
@Getter
@ToString
public class SubUserRequest {
    PermissionRequest permission;
    UserRequest user;
}
