package com.xingbingxuan.blog.admin.fenign;

import com.xingbingxuan.blog.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 评论微服务Feign 接口
 * @author : xbx
 * @date : 2022/5/4 17:29
 */
@FeignClient("my-blog-client")
public interface CommentFeignService {
    @GetMapping("/blogClient/message/count")
    public Result<Integer> queryCommentCount();

    @GetMapping("/blogClient/queryCommentCountByWeek")
    public Result queryCommentCountByWeek();
}
