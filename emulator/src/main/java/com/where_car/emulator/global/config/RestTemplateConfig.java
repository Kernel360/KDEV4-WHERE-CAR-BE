package com.where_car.emulator.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Value("${wherecar.api.rest.url}")
  private String restUrl;

  @Value("${wherecar.api.collector.url}")
  private String collectorUrl;

  @Value("${wherecar.api.hub.url}")
  private String hubUrl;

  @Bean(name = "restTemplate")
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.rootUri(restUrl).build();
  }

  @Bean(name = "collectorTemplate")
  public RestTemplate collectorTemplate(RestTemplateBuilder builder) {
    return builder.rootUri(collectorUrl).build();
  }

  @Bean(name = "hubTemplate")
  public RestTemplate hubTemplate(RestTemplateBuilder builder) {
    return builder.rootUri(hubUrl).build();
  }
}
