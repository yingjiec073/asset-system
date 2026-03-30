package com.example.assetsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final PermissionInterceptor permissionInterceptor;
    private final RateLimitInterceptor rateLimitInterceptor;

    public WebMvcConfig(PermissionInterceptor permissionInterceptor, RateLimitInterceptor rateLimitInterceptor) {
        this.permissionInterceptor = permissionInterceptor;
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor).addPathPatterns("/api/**");
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/api/**");
    }
}
