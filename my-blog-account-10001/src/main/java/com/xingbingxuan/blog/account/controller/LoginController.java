package com.xingbingxuan.blog.account.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.account.vo.UserLoginVo;
import com.xingbingxuan.blog.utils.Result;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xbx
 * @date : 2022/3/24 21:29
 */
@RestController
@RequestMapping("/account")
public class LoginController {


    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginVo userLoginVo){
        //调用服务层去查询数据库
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("1111");
        return Result.success(userEntity);
    }


    @PostMapping("/giteeLogin")
    public Result giteeLogin(@RequestBody String accessToken){

        UserEntity userEntity = accountService.giteeLogin(accessToken);

        if (userEntity == null){

            return Result.error("登录失败");
        }else {
            return Result.success("登录成功",userEntity);
        }
    }
}
