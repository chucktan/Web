package com.imooc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author
 * @create 2020-09-30-15:52
 * @content 打包war[4] 增加war的启动类
 */
public class WarStarterApplication  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //指向Application这个Springboot启动类
        return builder.sources(Application.class);
    }
}
