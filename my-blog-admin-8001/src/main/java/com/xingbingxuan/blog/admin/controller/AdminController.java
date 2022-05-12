package com.xingbingxuan.blog.admin.controller;

import com.alibaba.fastjson.JSON;
import com.xingbingxuan.blog.admin.service.AdminService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xbx
 * @date : 2022/5/4 21:55
 */
@RestController
@RequestMapping("adminService")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @RequestMapping("count")
    public Result queryCount(){

        JSON json = adminService.blogAndUserCount();

        return Result.success(json);
    }
}
