package com.xingbingxuan.blog.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : xbx
 * @date : 2022/3/24 19:15
 */
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient

public class AccountMain10001 {
    public static void main(String[] args) {
        SpringApplication.run(AccountMain10001.class,args);
    }
}
