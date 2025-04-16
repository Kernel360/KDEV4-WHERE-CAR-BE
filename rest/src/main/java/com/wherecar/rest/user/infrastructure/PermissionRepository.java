package com.wherecar.rest.user.infrastructure;

import com.wherecar.rest.user.domain.Permission;
import com.wherecar.rest.user.domain.constant.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByType(PermissionType type);
}
