package com.example.Sahtech.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mapperconfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
