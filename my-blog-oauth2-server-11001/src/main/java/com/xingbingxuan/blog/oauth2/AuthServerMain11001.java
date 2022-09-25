package com.xingbingxuan.blog.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author : xbx
 * @date : 2022/3/24 21:58
 */

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class AuthServerMain11001 {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerMain11001.class,args);
    }
}
