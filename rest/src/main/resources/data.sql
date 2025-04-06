INSERT INTO permissions (type) VALUES ('PERM_COMPANY_VIEW');
INSERT INTO permissions (type) VALUES ('PERM_COMPANY_EDIT');

-- 직원 관련 권한
INSERT INTO permissions (type) VALUES ('PERM_EMPLOYEE_VIEW');
INSERT INTO permissions (type) VALUES ('PERM_EMPLOYEE_ADD');
INSERT INTO permissions (type) VALUES ('PERM_EMPLOYEE_EDIT');
INSERT INTO permissions (type) VALUES ('PERM_EMPLOYEE_DELETE');

-- 권한 관리 관련 권한
INSERT INTO permissions (type) VALUES ('PERM_PERMISSION_VIEW');
INSERT INTO permissions (type) VALUES ('PERM_PERMISSION_EDIT');

-- 차량 관련 권한
INSERT INTO permissions (type) VALUES ('PERM_VEHICLE_VIEW');
INSERT INTO permissions (type) VALUES ('PERM_VEHICLE_ADD');
INSERT INTO permissions (type) VALUES ('PERM_VEHICLE_EDIT');
INSERT INTO permissions (type) VALUES ('PERM_VEHICLE_DELETE');

-- 로그 관련 권한
INSERT INTO permissions (type) VALUES ('PERM_LOGS_VIEW');
INSERT INTO permissions (type) VALUES ('PERM_LOGS_EXPORT');

-- 대시보드 관련 권한
INSERT INTO permissions (type) VALUES ('PERM_DASHBOARD_VIEW');
INSERT INTO permissions (type) VALUES ('PERM_DASHBOARD_EDIT');

-- 관리자 권한
INSERT INTO permissions (type) VALUES ('PERM_ADMIN');


SHOW CREATE TABLE permissions;

SHOW COLUMNS FROM permissions LIKE 'type';