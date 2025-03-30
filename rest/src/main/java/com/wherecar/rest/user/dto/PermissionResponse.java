package com.wherecar.rest.user.dto;

import com.wherecar.rest.user.domain.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse {
    private Set<PermissionType> permissionTypes = new HashSet<>();
}
