package com.xingbingxuan.blog.auth.feign;

import com.xingbingxuan.blog.auth.vo.UserLoginVo;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



/**
 * @author : xbx
 * @date : 2022/3/24 22:16
 */
@FeignClient("my-blog-account")
public interface AccountFeignService {

    @PostMapping("/account/login")
    Result login(@RequestBody UserLoginVo userLoginVo);

    @PostMapping("/account/giteeLogin")
    public Result giteeLogin(@RequestBody String accessToken);
}
