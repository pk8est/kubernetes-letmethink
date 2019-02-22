package com.pkest.web.api.config;

/**
 * Created by wuzhonggui on 2018/4/9.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC 配置
 */
@Slf4j
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Value("${spring.profiles.active}")
    private String env;


    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT");
    }
}
