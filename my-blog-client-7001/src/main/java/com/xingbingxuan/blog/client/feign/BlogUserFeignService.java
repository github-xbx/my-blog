package com.xingbingxuan.blog.client.feign;

import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * 用户服务feign接口
 * @author : xbx
 * @date : 2022/6/12 10:13
 */

@FeignClient("my-blog-account")
public interface BlogUserFeignService {

    /**
     * 功能描述:
     * <p>获取博客用户的信息 k => blogId ,v => userInfo</p>
     *
     * @param blogIdAndUserId
     * @return : java.util.Map
     * @author : xbx
     * @date : 2022/8/28 16:50
     */
    @PostMapping("user/queryUserHeaderByIds")
    public Map queryUserHeaderByIds(Map blogIdAndUserId);

    @GetMapping("userFollow/queryUserFollowUserId/{userId}")
    public Result<List<Integer>> queryUserFollowUserId(@PathVariable("userId") Integer userId);
}
