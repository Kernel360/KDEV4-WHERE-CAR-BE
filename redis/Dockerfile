FROM redis:7.2-alpine

# 설정 파일 복사
COPY redis.conf /usr/local/etc/redis/redis.conf

# Redis 실행 시 설정 파일 사용
CMD ["redis-server", "/usr/local/etc/redis/redis.conf"]
