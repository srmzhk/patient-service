package com.srmzhk.patientservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        // config DateTime Jackson parser for format: "yyyy-MM-dd'T'HH:mm:ss"
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
