package com.xingbingxuan.blog.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xingbingxuan.blog.admin.fenign.BlogFeignService;
import com.xingbingxuan.blog.admin.fenign.CommentFeignService;
import com.xingbingxuan.blog.admin.fenign.UserFeignService;
import com.xingbingxuan.blog.admin.service.AdminService;
import com.xingbingxuan.blog.utils.RedisUtil;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xbx
 * @date : 2022/5/4 22:03
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BlogFeignService blogFeignService;
    @Autowired
    private CommentFeignService commentFeignService;
    @Autowired
    private UserFeignService userFeignService;

    @Override
    public JSON blogAndUserCount() {
        JSONObject json  = new JSONObject();
        Result<Integer> blogCount = blogFeignService.queryBlogCount();
        if (blogCount.getCode() == 200){
            json.put("blogCount",blogCount.getObject());
        }else {
            json.put("blogCount",0);
        }
        Result<Integer> accountCount = userFeignService.queryAccountCount();
        if (accountCount.getCode() == 200){
            json.put("accountCount",accountCount.getObject());
        }else {
            json.put("accountCount",0);
        }
        Result<Integer> commentCount = commentFeignService.queryCommentCount();
        if (commentCount.getCode() == 200){
            json.put("commentCount",commentCount.getObject());
        }else {
            json.put("commentCount",0);
        }

        Object o = RedisUtil.get("blog:allReadCount");
        Integer integer = Integer.parseInt((String) o);
        json.put("readCount",integer);
        return json;
    }
}
