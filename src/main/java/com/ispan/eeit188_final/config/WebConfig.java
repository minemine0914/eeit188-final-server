package com.ispan.eeit188_final.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ispan.eeit188_final.util.JwtInterceptor;

import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    @SuppressWarnings("null")
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/user/**")
                .excludePathPatterns(
                        "/user/find/**",
                        "/user/login",
                        "/user/forgot-password",
                        "/user/check-password/**",
                        "/user/set-new-password/**",
                        "/user/createUser");
    }
}
