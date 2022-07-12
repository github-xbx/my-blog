package com.xingbingxuan.blog.auth.feign;

import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author : xbx
 * @date : 2022/6/30 22:05
 */
@FeignClient("my-blog-account")
public interface Oauth2UserFeignService {

    @PostMapping("/oauth2/userLogin")
    public Result<UserVo> userLogin(@RequestBody String userName);
}
