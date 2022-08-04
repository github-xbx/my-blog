package com.xingbingxuan.blog.account.controller;

import com.xingbingxuan.blog.account.service.UserFollowService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/6/21 22:14
 */
@RestController
@RequestMapping("userFollow")
public class UserFollowController {

    @Autowired
    private UserFollowService userFollowService;

    /**
     * 功能描述:
     * <p>根据用户id获取改用关注的用户id</p>
     *
     * @param userId
     * @return : com.xingbingxuan.blog.utils.Result<java.util.List<java.lang.Integer>>
     * @author : xbx
     * @date : 2022/6/21 22:18
     */
    @GetMapping("queryUserFollowUserId/{userId}")
    public Result<List<Integer>> queryUserFollowUserId(@PathVariable("userId") Integer userId){

        List<Integer> list = userFollowService.queryUserFollowIdByUserId(userId);
        return Result.success(list);
    }
}
