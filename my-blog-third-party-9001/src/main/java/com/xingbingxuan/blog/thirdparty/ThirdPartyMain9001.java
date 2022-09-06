package com.xingbingxuan.blog.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 第三方服务主启动类
 * @author : xbx
 * @date : 2022/7/23 9:07
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ThirdPartyMain9001 {

    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyMain9001.class,args);
    }
}
