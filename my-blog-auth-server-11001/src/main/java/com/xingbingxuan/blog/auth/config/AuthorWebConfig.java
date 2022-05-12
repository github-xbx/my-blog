package com.xingbingxuan.blog.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : xbx
 * @date : 2022/3/24 22:01
 */
@Configuration
public class AuthorWebConfig implements WebMvcConfigurer {

    /**
     * 视图映射
     *
     * @author xbx
     * @version 1.0
     * @date 2022/3/24 22:02
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //只是get请求映射
        registry.addViewController("/login.html").setViewName("login");
    }
}
