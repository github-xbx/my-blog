package com.xingbingxuan.blog.account.feign;

import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.token.AccessToken;
import com.xingbingxuan.blog.token.Authentication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权服务的feign接口
 * @author : xbx
 * @date : 2022/10/1 10:47
 */
@FeignClient("my-blog-authorize-server")
public interface AccountAuthorizeServiceFeign {

    /**
     * 功能描述:
     * <p>第三方应用登录,获取token</p>
     *
     * @param user
     * @return : com.xingbingxuan.blog.token.AccessToken
     * @author : xbx
     * @date : 2022/9/25 15:41
     */
    @PostMapping("auth/loginToken")
    public AccessToken loginToken(@RequestBody UserAllInfoDto user);


    /**
     * 功能描述:
     * <p>验证token</p>
     *
     * @return : com.xingbingxuan.blog.token.Authentication
     * @author : xbx
     * @date : 2022/10/5 16:54
     */
    @PostMapping("auth/checkToken")
    public Authentication checkToken();

    /**
     * 功能描述:
     * <p>删除存储的token，用户用户退出登录</p>
     *
     * @return : java.lang.Boolean
     * @author : xbx
     * @date : 2022/10/6 15:49
     */
    @PostMapping("auth/deleteToken")
    public Boolean deleteToken();
}
