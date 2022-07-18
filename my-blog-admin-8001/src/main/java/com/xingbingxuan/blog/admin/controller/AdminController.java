package com.xingbingxuan.blog.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xingbingxuan.blog.admin.service.AdminService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/5/4 21:55
 */
@RestController
@RequestMapping("adminService")
public class AdminController {

    @Autowired
    private AdminService adminService;


    /**
     * 功能描述:
     * <p>获取博客、用户、留言、阅读数</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/5/14 22:39
     */
    @RequestMapping("info/count")
    public Result queryCount(){

        JSONObject json = (JSONObject) adminService.blogAndUserCount();

        List list = adminService.getChartData();
        json.put("statistics",list);

        return Result.success(json);
    }


}
