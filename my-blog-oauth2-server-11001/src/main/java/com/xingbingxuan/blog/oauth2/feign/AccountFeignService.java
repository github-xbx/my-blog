package com.xingbingxuan.blog.oauth2.feign;

import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.param.UserParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author : xbx
 * @date : 2022/3/24 22:16
 */
@FeignClient("my-blog-account")
public interface AccountFeignService {

    /**
     * 功能描述:
     * <p>根据用户名获取用户信息，oauth2 普通用户登录查询</p>
     *
     * @param userName
     * @return : com.xingbingxuan.blog.dto.UserAllInfoDto
     * @author : xbx
     * @date : 2022/9/5 21:56
     */
    @PostMapping("/userFeignInterface/userLogin")
    public UserAllInfoDto userLogin(@RequestBody String userName);

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
