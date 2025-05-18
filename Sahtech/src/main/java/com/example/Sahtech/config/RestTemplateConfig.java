package com.example.Sahtech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        
        // Set timeout to 5 seconds for connection and 10 seconds for read
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(10000);
        
        RestTemplate restTemplate = new RestTemplate(factory);
        
        // Create a custom ObjectMapper with more lenient settings
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        
        // Create a message converter with our custom ObjectMapper
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        
        // Set the custom message converter as the only converter to ensure it's used
        restTemplate.setMessageConverters(Collections.singletonList(messageConverter));
        
        return restTemplate;
    }
} 