# 빌드 스테이지
FROM wherecar/base:1.0.0 AS builder

# 작업 디렉토리 설정
WORKDIR /build

# Gradle 래퍼 및 빌드 설정 파일 복사
COPY gradlew .
COPY gradle gradle
COPY settings.gradle .
COPY build.gradle .
COPY gradle.properties .

# Gradle 실행 권한 설정
RUN chmod +x ./gradlew

# 종속성 다운로드 (캐시 레이어 활용)
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사 및 빌드
COPY src src
RUN ./gradlew build -x test --no-daemon

# 실행 스테이지
FROM eclipse-temurin:17-jre

WORKDIR /app

# 필요한 도구만 설치
RUN apt-get update && \
    apt-get install -y curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 빌드 스테이지에서 JAR 파일 복사
COPY --from=builder /build/build/libs/*.jar batch.jar

# 환경 변수 설정
ENV SPRING_PROFILES_ACTIVE=prod

# 포트 노출
EXPOSE 8087

# 실행 명령
ENTRYPOINT ["java", "-jar", "batch.jar"]
