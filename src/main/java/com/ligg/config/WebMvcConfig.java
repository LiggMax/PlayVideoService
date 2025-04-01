package com.ligg.config;

import com.ligg.interceptor.JwtAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册JWT拦截器，只拦截/api/user/**路径下的接口，但排除OPTIONS请求和一些特定接口
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .addPathPatterns("/api/user/**")  // 只拦截用户相关API
                .addPathPatterns("/api/comment/**")  // 拦截评论相关API（获取评论列表接口除外）
                .excludePathPatterns("/api/account/**")  // 不拦截账户相关API
                .excludePathPatterns("/api/comment/video/**")  // 不拦截获取评论列表API
                .excludePathPatterns("/**/*.html", "/**/*.js", "/**/*.css", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");  // 不拦截静态资源
    }
} 