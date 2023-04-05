package com.xingbingxuan.blog.account.controller;

import com.xingbingxuan.blog.account.entity.AdminEntity;
import com.xingbingxuan.blog.account.service.AdminService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xbx
 * @date : 2022/5/3 22:39
 */
@RestController
@RequestMapping("admin/account")
public class AdminAccountController {


    @Autowired
    private AdminService adminService;

    /**
     * 功能描述:
     * <p>管理员登录</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/5/3 22:43
     */
    @RequestMapping("login")
    @PostMapping
    public Result adminLogin(@RequestBody AdminEntity adminEntity){

        String login = adminService.login(adminEntity);

        if ("2".equals(login)){
            return Result.error("用户不存在！！！");
        }
        if ("0".equals(login)){
            return Result.error("密码错误！！！");
        }
        if ("3".equals(login)){
            return Result.error("系统错误！！！");
        }
        return Result.success(login);
    }


    @RequestMapping("addAdmin")
    @PostMapping
    public Result addAdmin(@RequestBody AdminEntity adminEntity){

        Integer integer = adminService.addAdmin(adminEntity);
        if (integer>0){
            if (integer == 2){
                return Result.error("用户已存在！！！");
            }
            return Result.success();
        }else {
            return Result.error();
        }


    }
}
