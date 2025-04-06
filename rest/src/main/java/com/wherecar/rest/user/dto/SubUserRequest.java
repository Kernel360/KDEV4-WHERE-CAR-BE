package com.wherecar.rest.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubUserRequest {
    PermissionRequest permission;
    UserRequest user;
}
