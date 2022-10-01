package com.xingbingxuan.blog.authorize.controller;

import com.xingbingxuan.blog.authorize.service.TokenService;
import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.token.AccessToken;
import com.xingbingxuan.blog.token.Authentication;
import com.xingbingxuan.blog.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : xbx
 * @date : 2022/9/25 10:45
 */
@RestController
@RequestMapping("auth")
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

        AccessToken accessToken = this.tokenService.createAccessToken(authentication);

        return accessToken;
    }

    //验证（token）


    //登出（退出登录）


}
