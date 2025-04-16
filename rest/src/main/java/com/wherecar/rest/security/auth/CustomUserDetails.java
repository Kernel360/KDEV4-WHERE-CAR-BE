package com.wherecar.rest.security.auth;

import com.wherecar.rest.user.domain.constant.PermissionType;
import com.wherecar.rest.user.domain.User;
import com.wherecar.rest.user.domain.UserPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private final User user;


    public CustomUserDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return new ArrayList<>();
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {

        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

    public Set<PermissionType> getPermissionTypes() {
        Set<UserPermission> userPermissions = user.getUserPermissions();
        Set<PermissionType> permissionTypes = new HashSet<>();
        for (UserPermission userPermission : userPermissions) {
            permissionTypes.add(userPermission.getPermission().getType());
        }
        return permissionTypes;
    }

    public Long getCompanyId(){
        return user.getCompany().getId();
    }

    public Long getUserId(){
        return user.getId();
    }
}
