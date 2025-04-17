# 빌드 스테이지
FROM wherecar/base:latest AS builder

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 캐시 최적화를 위해 의존성 관련 파일 먼저 복사
COPY ./build.gradle ./
COPY ./settings.gradle ./
COPY ./gradle ./gradle
COPY ./gradlew ./

# gradlew 스크립트에 실행 권한을 부여
RUN chmod +x ./gradlew

# 의존성 미리 다운로드
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY ./rest ./rest

# API 모듈과 에뮬레이터 모듈 빌드
RUN ./gradlew :rest:build -x test --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app

# 빌드된 JAR 파일만 복사
COPY --from=builder /app/rest/build/libs/rest-*.jar ./rest.jar

# 환경 변수 설정
ENV SPRING_PROFILES_ACTIVE=prod

# 포트 노출
EXPOSE 8080

# 실행 명령
ENTRYPOINT ["java", "-jar", "./rest.jar"]
