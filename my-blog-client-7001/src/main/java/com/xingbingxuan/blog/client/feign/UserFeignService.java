package com.xingbingxuan.blog.client.feign;

import com.xingbingxuan.blog.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * 用户服务feign接口
 * @author : xbx
 * @date : 2022/6/12 10:13
 */

@FeignClient("my-blog-account")
public interface UserFeignService {

    @PostMapping("user/queryUserHeaderByIds")
    public Map queryUserHeaderByIds(Map blogIdAndUserId);
}
