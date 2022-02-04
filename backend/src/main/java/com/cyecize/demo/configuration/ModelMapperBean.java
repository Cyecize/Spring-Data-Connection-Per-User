package com.cyecize.demo.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperBean {

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
