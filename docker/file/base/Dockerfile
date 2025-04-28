FROM eclipse-temurin:17-jdk

WORKDIR /app

# 필요한 도구 설치 (최소화)
RUN apt-get update && \
    apt-get install -y curl unzip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Gradle 관련 환경 변수 설정
ENV GRADLE_HOME=/opt/gradle
ENV PATH=$PATH:$GRADLE_HOME/bin

# 빌드 캐시 레이어 최적화를 위한 볼륨 지정
VOLUME /root/.gradle
