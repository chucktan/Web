package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域实现前后台联调
 * @author
 * @create 2020-06-29-10:57
 */
@Configuration
public class CorsConfig {

    public CorsConfig() {
    }

    @Bean
    public CorsFilter corsFilter(){

        //1.添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://shop.btrade.cloud:8080");
        config.addAllowedOrigin("http://center.btrade.cloud:8080");
        config.addAllowedOrigin("http://shop.btrade.cloud");
        config.addAllowedOrigin("http://center.btrade.cloud");

        //设置是否发送cookie信息
        config.setAllowCredentials(true);

        //设置允许的请求方式
        config.addAllowedMethod("*");

        //设置运行的headers
        config.addAllowedHeader("*");

        //2.为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**",config);

        //3.返回重新定义好的corsSource
        return  new CorsFilter(corsSource);

    }
}
