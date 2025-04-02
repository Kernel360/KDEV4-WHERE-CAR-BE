package com.wherecar.rest.user.permissionCheck;

import com.wherecar.rest.user.auth.AuthUtil;
import com.wherecar.rest.user.domain.PermissionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
@Order(1) // AOP 실행 순서 설정 (낮을수록 우선 실행)
@Slf4j
@RequiredArgsConstructor
public class PermissionCheckAspect {

    @Around("@annotation(requiredPermission)")
    public Object checkPermissions(ProceedingJoinPoint joinPoint, RequiredPermission requiredPermission) throws Throwable {
        Set<PermissionType> userPermissions = AuthUtil.getPermissionTypes();

        PermissionType[] requiredPermissions = requiredPermission.value();

        //루트 권한일때는 자동으로 패스
        if (userPermissions.contains(PermissionType.ROOT)){
            return joinPoint.proceed();
        }
        for (PermissionType required : requiredPermissions) {
            if (userPermissions.contains(required)) {
                return joinPoint.proceed(); // 메서드 실행
            }
        }

        throw new RuntimeException("No Permission found for required permission: " + requiredPermission);
    }
}
