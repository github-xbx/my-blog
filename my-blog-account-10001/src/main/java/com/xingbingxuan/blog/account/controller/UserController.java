package com.xingbingxuan.blog.account.controller;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("userPage")
    public Result<PageInfo<UserEntity>> queryAllUserPage(@RequestBody Map map){

        PageInfo<UserEntity> pageInfo = accountService.queryAllUserPage((Integer) map.get("pageNum"), (Integer) map.get("pageSize"));

        return Result.success(pageInfo);
    }

    /**
     * 功能描述:
     * <p>获取注册用户的个数</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result<java.lang.Integer>
     * @author : xbx
     * @date : 2022/5/14 17:55
     */
    @GetMapping("accountCount")
    public Result<Integer> queryAccountCount(){
        Integer count = accountService.queryAccountCount();
        return Result.success(count);
    }

    /**
     * 功能描述:
     * <p>获得最近七天注册的用户数</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/5/14 17:57
     */
    @GetMapping("accountByWeek")
    public Result accountByWeek(){

        List list = accountService.queryAccountCountByThisWeek();

        return Result.success(list);
    }
}
