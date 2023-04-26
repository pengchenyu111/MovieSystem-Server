package com.pcy.config;

import com.pcy.commmon.web.interceptor.FeignRequestInterceptor;
import com.pcy.commmon.web.interceptor.RequireLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 添加请求拦截器
 * 在controller中使用@RequireLogin注解，表示该请求需要登录才能访问
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public RequireLoginInterceptor requireLoginInterceptor(StringRedisTemplate redisTemplate) {
        return new RequireLoginInterceptor(redisTemplate);
    }

    @Bean
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }

    @Autowired
    private RequireLoginInterceptor requireLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requireLoginInterceptor)
                .addPathPatterns("/**");
    }
}