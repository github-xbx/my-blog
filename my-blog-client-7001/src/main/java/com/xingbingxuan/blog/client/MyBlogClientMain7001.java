package com.xingbingxuan.blog.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : xbx
 * @date : 2022/3/24 15:15
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class MyBlogClientMain7001 {

    public static void main(String[] args) {
        SpringApplication.run(MyBlogClientMain7001.class,args);
    }
}
