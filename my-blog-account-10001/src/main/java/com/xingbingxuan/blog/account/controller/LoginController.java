package com.xingbingxuan.blog.account.controller;

import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.account.entity.vo.UserLoginVo;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author : xbx
 * @date : 2022/3/24 21:29
 */
@Slf4j
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
