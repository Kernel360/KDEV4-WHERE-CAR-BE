package com.wherecar.rest.user.dto;

import com.wherecar.rest.user.domain.PermissionType;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Data
@Value
@Builder
public class PermissionRequest {
    @Builder.Default
    private Set<PermissionType> permissionTypes = new HashSet<>();
}
