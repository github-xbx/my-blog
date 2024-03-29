package com.xingbingxuan.blog.thirdparty.feign;

import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.param.UserParam;
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
     * <p>根据第三方登录的类型和uid获取用户信息，oauth2 第三方应用登录</p>
     *
     * @param userParam
     * @return : com.xingbingxuan.blog.dto.UserAllInfoDto
     * @author : xbx
     * @date : 2022/9/5 22:01
     */
    @PostMapping("/userFeignInterface/thirdPartyLogin")
    public UserAllInfoDto thirdPartyLogin(@RequestBody UserParam userParam);
}
