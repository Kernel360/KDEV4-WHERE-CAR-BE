package com.wherecar.rest.user.infrastructure;

import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.domain.constant.PermissionType;

import java.util.Set;

public interface UserStore {
    User store(User user);
    User store(User user, Set<PermissionType> permissionTypes);
    void deleteById(Long userId);
}
