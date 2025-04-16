package com.wherecar.rest.user.application.dto;

import com.wherecar.rest.user.domain.constant.PermissionType;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PermissionRequest {
    private Set<PermissionType> permissionTypes;
}
