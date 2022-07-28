package com.xingbingxuan.blog.thirdparty.feign;

import com.xingbingxuan.blog.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author : xbx
 * @date : 2022/7/25 22:30
 */
@FeignClient("my-blog-account")
public interface AccountServiceFeign {
    /**
     * 功能描述:
     * <p> 调用账户服务，并绑定该系统账户</p>
     *
     * @param param
     * @return : com.xingbingxuan.blog.vo.UserVo
     * @author : xbx
     * @date : 2022/7/25 22:40
     */
    @PostMapping("user/thirdLogin")
    public UserVo loginAndRegister(@RequestBody Map param);
}
