package com.wherecar.rest.common.permissionCheck;

import com.wherecar.rest.user.domain.constant.PermissionType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequiredPermission {
    PermissionType[] value(); // 필요한 권한을 설정할 수 있도록 배열로 받음
}
