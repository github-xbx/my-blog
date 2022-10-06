package com.xingbingxuan.blog.authorize.service;

import com.xingbingxuan.blog.token.AccessToken;
import com.xingbingxuan.blog.token.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权token服务接口
 * @author : xbx
 * @date : 2022/9/24 22:16
 */
public interface TokenService {

    /**
     * 功能描述:
     * <p>创建一个用户的token，如果该用户存在token在redis中，则删除之后再创建一个</p>
     *
     * @param authentication
     * @return : com.xingbingxuan.blog.token.AccessToken
     * @author : xbx
     * @date : 2022/9/24 22:18
     */
    public AccessToken createAccessToken(Authentication authentication);

    /**
     * 功能描述:
     * <p>删除一个用户的token</p>
     *
     * @param request
     * @return : com.xingbingxuan.blog.token.AccessToken
     * @author : xbx
     * @date : 2022/9/24 22:25
     */
    public Boolean removeAccessToken(HttpServletRequest request);

    /**
     * 功能描述:
     * <p>加载身份验证,用于验证用户token 是否存在、是否正确</p>
     *
     * @param requeste
     * @return : com.xingbingxuan.blog.token.Authentication
     * @author : xbx
     * @date : 2022/9/24 22:21
     */
    public Authentication loadAuthentication(HttpServletRequest requeste);


}
