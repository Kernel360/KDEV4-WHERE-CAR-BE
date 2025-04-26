package com.wherecar.rest.user.application.dto;

import com.wherecar.rest.user.domain.constant.PermissionType;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@ToString
public class PermissionRequest {
    private Set<PermissionType> permissionTypes;
}
