package com.xingbingxuan.blog.account.controller;

import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户操作控制器
 * @author : xbx
 * @date : 2022/5/2 9:48
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private AccountService accountService;

    @GetMapping("accountCount")
    public Result<Integer> queryAccountCount(){
        Integer count = accountService.queryAccountCount();
        return Result.success(count);
    }
}
