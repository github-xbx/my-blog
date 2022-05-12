package com.xingbingxuan.blog.admin.config;

import com.xingbingxuan.blog.admin.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : xbx
 * @date : 2022/5/6 22:31
 */

public class AdminInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有 但是静态资源也会被拦截住
        // .excludePathPatterns("/login", "/", "login.html") 表示不拦截哪些
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/", "login.html", "/css/**", "/fonts/**", "/images/**", "/js/**");
    }
}
