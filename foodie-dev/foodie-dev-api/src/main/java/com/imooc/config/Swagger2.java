package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Api接口配置
 * @author
 * @create 2020-06-28-14:47
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    //访问地址
    //http://localhost:8088/swagger-ui.html 原路径
    //http://localhost:8088/doc.html 改装路径


    //配置Swagger2核心配置：docket
    @Bean
    public Docket createRestApi()
    {
        return  new Docket(DocumentationType.SWAGGER_2)                        //指定api类型为swagger2
                        .apiInfo(apiInfo())                                    //用于定义api文档汇总信息
                        .select()
                        .apis(RequestHandlerSelectors.
                                basePackage("com.imooc.controller"))            //指定controller的包
                        .paths(PathSelectors.any())                             //所有controller
                        .build();
    }

    private ApiInfo apiInfo(){
        return  new ApiInfoBuilder()
                .title("天天吃货 电商平台接口Api") //文档页标题
                .contact(new Contact("imooc",
                        "https://www.imooc.com",
                        "abc@qq.com"))//联系人信息
                .description("专门为天天吃货提供的api文档")//详细信息
                .version("1.0.1") //文档版本号
                .termsOfServiceUrl("https://www.imooc.com")//网站地址
                .build();
    }
}
