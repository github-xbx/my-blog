package com.xingbingxuan.blog.admin.fenign;

import com.xingbingxuan.blog.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : xbx
 * @date : 2022/5/4 21:53
 */
@FeignClient("my-blog-account")
public interface UserFeignService {

    @GetMapping("/user/accountCount")
    public Result<Integer> queryAccountCount();
}
