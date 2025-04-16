package com.wherecar.rest.common.config;

import com.wherecar.rest.user.domain.Permission;
import com.wherecar.rest.user.domain.constant.PermissionType;
import com.wherecar.rest.user.infrastructure.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PermissionConfig {
    private final PermissionRepository permissionRepository;

    @Bean
    public Map<PermissionType, Permission> permissionMap(){
        Map<PermissionType, Permission> permissionMap = new HashMap<>();
        for (PermissionType type : PermissionType.values()) {
            Permission permission = permissionRepository.findByType(type).orElseThrow();
            permissionMap.put(type, permission);
        }
        return permissionMap;
    }
}
