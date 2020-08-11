package com.imooc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * web请求配置
 * @author
 * @create 2020-07-24-10:13
 */
@Configuration
public class WebMvcConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return  builder.build();
    }
}
