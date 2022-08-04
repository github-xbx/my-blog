package com.xingbingxuan.blog.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : xbx
 * @date : 2022/3/15 23:21
 */
@SpringBootApplication
@EnableDiscoveryClient //让注册中心发现注册到注册中心
public class GateWayMain80 {

    public static void main(String[] args) {
        SpringApplication.run(GateWayMain80.class,args);


    }
}
