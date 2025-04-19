# wherecar/base:latest 이미지 생성용 Dockerfile

FROM eclipse-temurin:17-jre

# 작업 디렉토리 설정
WORKDIR /app

# 필요한 도구 설치
RUN apt-get update && \
    apt-get install -y curl unzip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Gradle 관련 환경 변수 설정
ENV GRADLE_HOME=/opt/gradle
ENV PATH=$PATH:$GRADLE_HOME/bin
