package com.wherecar.rest.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customDateFormat() {
        return builder -> builder
                .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }
}