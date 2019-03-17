package com.pkest.web.api.config;

/**
 * Created by wuzhonggui on 2018/4/9.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Spring MVC 配置
 */
@Slf4j
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CompareBuilderArgumentResolver());
    }

    /*@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        Sort defaultSort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable defaultPage = new PageRequest(0, 10, defaultSort);

        SortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver = new SortHandlerMethodArgumentResolver();
        sortHandlerMethodArgumentResolver.setSortParameter("sort");
        sortHandlerMethodArgumentResolver.setFallbackSort(defaultSort);

        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();
        pageableResolver.setMaxPageSize(100);
        pageableResolver.setOneIndexedParameters(false);
        pageableResolver.setPageParameterName("page");
        pageableResolver.setSizeParameterName("size");
        pageableResolver.setFallbackPageable(defaultPage);

        argumentResolvers.add(sortHandlerMethodArgumentResolver);
        argumentResolvers.add(pageableResolver);
    }*/

    public class CorsConfig {
        @Bean
        public CorsFilter corsFilter() {
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOrigin("*");
            config.setAllowCredentials(true);
            config.addAllowedMethod("*");
            config.addAllowedHeader("*");
            UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
            configSource.registerCorsConfiguration("/**", config);
            return new CorsFilter(configSource);
        }
    }
}
