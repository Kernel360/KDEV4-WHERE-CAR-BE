package com.wherecar.rest.emulauth.hubtmp.config;

import com.wherecar.rest.emulauth.hubtmp.interceptor.TokenAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenAuthInterceptor tokenAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor((HandlerInterceptor) tokenAuthInterceptor)
                .addPathPatterns("/emulator/**")           // 검증할 API 경로
                .excludePathPatterns("/emulator/token");    // 토큰 발급은 예외
    }
}
