package com.mistifler.demo.springbootaop.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by mistifler on 2018/7/17.
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public AnnotationInterceptor annotationInterceptor() {
        return new AnnotationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(annotationInterceptor()).addPathPatterns("/**");
    }
}
