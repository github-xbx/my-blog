package com.xingbingxuan.blog.oauth2.feign;

import com.xingbingxuan.blog.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 点三方服务feign接口
 * @author : xbx
 * @date : 2022/9/5 17:06
 */
@FeignClient("my-blog-third-party")
public interface ThirdPartyFeignService {

    @GetMapping("/gitee/login")
    @ResponseBody
    public Result<String> getGiteeUrl();
}
