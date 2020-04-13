package com.damgor.foodapp.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching
public class BeansConfiguration {

    @Bean
    public RestTemplate getRestTemplate (){
        return new RestTemplate();
    }

}
