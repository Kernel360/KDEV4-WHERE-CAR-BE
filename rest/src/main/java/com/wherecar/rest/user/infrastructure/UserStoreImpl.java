package com.wherecar.rest.user.infrastructure;

import com.wherecar.rest.user.domain.Permission;
import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.domain.constant.PermissionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;
    private final Map<PermissionType, Permission> permissionMap;

    @Override
    public User store(User user) {
        return userRepository.save(user);
    }

    @Override
    public User store(User user, Set<PermissionType> permissionTypes) {
        Set<Permission> permissions = new HashSet<>();
        for (PermissionType permissionType : permissionTypes) {
            Permission permission = permissionMap.get(permissionType);
            permissions.add(permission);
        }
        user.changeUserPermissions(permissions);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long userId){
        userRepository.deleteById(userId);
    }
}
