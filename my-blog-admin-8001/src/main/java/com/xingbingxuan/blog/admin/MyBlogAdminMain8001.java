package com.xingbingxuan.blog.admin;

import com.sun.glass.ui.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : xbx
 * @date : 2022/5/3 21:35
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MyBlogAdminMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(MyBlogAdminMain8001.class,args);
    }
}
