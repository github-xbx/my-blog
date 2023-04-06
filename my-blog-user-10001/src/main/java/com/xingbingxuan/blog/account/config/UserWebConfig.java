package com.xingbingxuan.blog.account.config;

import com.xingbingxuan.blog.account.feign.AccountAuthorizeServiceFeign;
import com.xingbingxuan.blog.account.interceptor.RoleAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * account 服务web配置类
 * @author : xbx
 * @date : 2022/10/5 18:57
 */
@Configuration
public class UserWebConfig implements WebMvcConfigurer {




    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //registry.addInterceptor(new RoleAuthInterceptor()).addPathPatterns("/**");

    }
}
