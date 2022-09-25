package com.xingbingxuan.blog.thirdparty.feign;

import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.token.AccessToken;
import com.xingbingxuan.blog.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author : xbx
 * @date : 2022/9/25 16:55
 */
@FeignClient("my-blog-authorize-server")
public interface AuthorizeServiceFeign {

    /**
     * 功能描述:
     * <p>第三方应用登录,获取token</p>
     *
     * @param user
     * @return : com.xingbingxuan.blog.token.AccessToken
     * @author : xbx
     * @date : 2022/9/25 15:41
     */
    @PostMapping("auth/thirdPartyLoginToken")
    public AccessToken thirdPartyLoginToken(@RequestBody UserAllInfoDto user);

}
