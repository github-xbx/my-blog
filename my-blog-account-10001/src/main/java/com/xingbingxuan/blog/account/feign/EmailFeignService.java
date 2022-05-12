package com.xingbingxuan.blog.account.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : xbx
 * @date : 2022/3/25 20:52
 */
@FeignClient("my-blog-auth-server")
public interface EmailFeignService {

    @GetMapping("/auth2/email")
    public String emailAuthPage();
}
