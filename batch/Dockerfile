# === 1단계: 빌드 스테이지 ===
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# 현재 디렉토리 기준 (batch 내부 기준)
COPY gradlew .
RUN chmod +x gradlew
COPY gradle ./gradle
COPY build.gradle settings.gradle ./


# 전체 소스 복사
COPY . .

# batch 모듈 빌드 (루트가 batch이므로 bootJar만으로 충분)
RUN ./gradlew :bootJar

# === 2단계: 실행 이미지 ===
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
