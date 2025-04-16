package com.wherecar.rest.common.auth;

import com.wherecar.rest.user.domain.constant.PermissionType;
import com.wherecar.rest.common.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

public class AuthUtil {
    public static CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Not authenticated");
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails;
        }
        throw new RuntimeException("Not authenticated");
    }
    
    public static Long getCompanyId(){
        return getCurrentUserDetails().getCompanyId();
    }

    public static Set<PermissionType> getPermissionTypes(){
        return getCurrentUserDetails().getPermissionTypes();
    }

    public static Long getUserId(){
        return getCurrentUserDetails().getUserId();
    }
}
