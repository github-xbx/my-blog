package com.xingbingxuan.blog.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 账户服务启动类
 * @author : xbx
 * @date : 2022/3/24 19:15
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class UserMain10001 {
    public static void main(String[] args) {
        SpringApplication.run(UserMain10001.class,args);
    }
}
