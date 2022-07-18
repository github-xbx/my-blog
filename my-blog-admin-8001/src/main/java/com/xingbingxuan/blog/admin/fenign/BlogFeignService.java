package com.xingbingxuan.blog.admin.fenign;

import com.xingbingxuan.blog.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 博客微服务feign 接口
 * @author : xbx
 * @date : 2022/5/4 17:26
 */
@FeignClient(name = "my-blog-client",contextId = "adminFeign")
public interface BlogFeignService {
    @GetMapping("/blogClient/adminBlogCount")
    public Result<Integer> queryBlogCount();

    @GetMapping("/blogClient/queryBlogCountByWeek")
    public Result queryBlogCountByWeek();

}
