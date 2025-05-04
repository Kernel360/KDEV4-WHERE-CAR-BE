# === 1단계: 빌드 스테이지 ===
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Gradle wrapper 및 설정 복사
COPY gradlew .
RUN chmod +x gradlew
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

# hub 모듈 전체 복사 (이 Dockerfile이 hub 디렉토리에 있다고 가정)
COPY . .

# hub 모듈 빌드
RUN ./gradlew :bootJar

# === 2단계: 실행 이미지 ===
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 빌드된 JAR 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 프로필 설정 (환경변수는 ECS에서 주입)
ENV SPRING_PROFILES_ACTIVE=prod

# 앱 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
