package com.wherecar.rest.user.application.dto;

import com.wherecar.rest.user.domain.constant.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponse {
    private Set<PermissionType> permissionTypes;
}
