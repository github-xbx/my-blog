package com.xingbingxuan.blog.authorize.controller;

import com.xingbingxuan.blog.authorize.service.TokenService;
import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.token.AccessToken;
import com.xingbingxuan.blog.token.Authentication;
import com.xingbingxuan.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : xbx
 * @date : 2022/9/25 10:45
 */
@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {


    @Autowired
    private TokenService tokenService;


    //登录
    //1、普通用户用户名密码登录

    /**
     * 功能描述:
     * <p>第三方应用登录,获取token</p>
     *
     * @param user
     * @return : com.xingbingxuan.blog.token.AccessToken
     * @author : xbx
     * @date : 2022/9/25 15:41
     */
    @PostMapping("loginToken")
    public AccessToken loginToken(@RequestBody UserAllInfoDto user){

        Authentication authentication = new Authentication();
        authentication.setUserId(user.getId().toString());
        authentication.setUsername(user.getUsername());
        authentication.setScope("all");
        List<String> authority  = new ArrayList<>();
        user.getRoleVos().forEach(roleVo -> {
            authority.add(roleVo.getRoleCode());
        });
        authentication.setAuthorities(authority);

        AccessToken accessToken = this.tokenService.createAccessToken(authentication);

        //int i = 1/0;
        return accessToken;
    }

    /**
     * 功能描述:
     * <p>验证token</p>
     *
     * @return : com.xingbingxuan.blog.token.Authentication
     * @author : xbx
     * @date : 2022/10/5 16:52
     */
    @PostMapping("checkToken")
    public Authentication checkToken(){
        //获取请求request 信息
        ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        Authentication authentication = this.tokenService.loadAuthentication(request);
        log.info("检查 token -> {}",authentication.toString() );
        return authentication;
    }

    /**
     * 功能描述:
     * <p>删除存储的token，用户用户退出登录</p>
     *
     * @return : java.lang.Boolean
     * @author : xbx
     * @date : 2022/10/6 15:49
     */
    @PostMapping("deleteToken")
    public Boolean deleteToken(){
        //获取请求request 信息
        ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        return this.tokenService.removeAccessToken(request);
    }

}
