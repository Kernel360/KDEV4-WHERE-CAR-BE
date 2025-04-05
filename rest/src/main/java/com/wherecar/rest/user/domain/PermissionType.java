package com.wherecar.rest.user.domain;

public enum PermissionType {
    PERM_COMPANY_VIEW,
    PERM_COMPANY_EDIT,

    // 직원 관련 권한
    PERM_EMPLOYEE_VIEW,
    PERM_EMPLOYEE_ADD,
    PERM_EMPLOYEE_EDIT,
    PERM_EMPLOYEE_DELETE,

    // 권한 관리 관련 권한
    PERM_PERMISSION_VIEW,
    PERM_PERMISSION_EDIT,

    // 차량 관련 권한
    PERM_VEHICLE_VIEW,
    PERM_VEHICLE_ADD,
    PERM_VEHICLE_EDIT,
    PERM_VEHICLE_DELETE,

    // 로그 관련 권한
    PERM_LOGS_VIEW,
    PERM_LOGS_EXPORT,

    // 대시보드 관련 권한
    PERM_DASHBOARD_VIEW,
    PERM_DASHBOARD_EDIT,

    // 관리자 권한
    PERM_ADMIN

}
