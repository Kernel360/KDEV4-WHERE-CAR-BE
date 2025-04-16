INSERT INTO wherecar.announcements (announcement_type, title, content, created_at, updated_at)
VALUES ('INFO', '시스템 점검 안내', '시스템 점검으로 인해 일부 서비스 이용이 제한됩니다. 자세한 사항은 공지사항을 참고해주세요.', NOW(), NOW());

INSERT INTO wherecar.announcements (announcement_type, title, content, created_at, updated_at)
VALUES ('ALERT', '긴급 서버 장애', '현재 서버 장애로 인해 서비스 접속이 원활하지 않습니다. 빠른 시간 내에 복구하겠습니다.', NOW(), NOW());

INSERT INTO wherecar.announcements (announcement_type, title, content, created_at, updated_at)
VALUES ('INFO', '신규 기능 출시', '고객님의 편의를 위해 신규 예약 기능이 추가되었습니다. 많은 이용 부탁드립니다.', NOW(), NOW());
