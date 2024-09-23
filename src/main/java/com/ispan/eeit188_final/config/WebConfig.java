package com.ispan.eeit188_final.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ispan.eeit188_final.util.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    @SuppressWarnings("null")
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/user/**", "/chat-record/**")
                .excludePathPatterns(
                        "/user/find/**",
                        "/user/find-users",
                        "/user/login",
                        "/user/forgot-password",
                        "/user/createUser",
                        "/user/system/forgot-password");
    }
}
