package com.where_car.emulator.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Value("${where-car.server.url}")
  private String serverUrl;

  // restTemplate Bean으로 등록
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .rootUri(serverUrl) // 호출할 API 서비스 도메인 URL
        // ... 기타 설정 필요하면 추가 ...
        .build();
  }
}
