package com.wherecar.rest.user.dto;

import com.wherecar.rest.user.domain.PermissionType;
import lombok.Data;

import java.util.Set;

@Data
public class PermissionRequest {
    private Set<PermissionType> permissionTypes;
}
