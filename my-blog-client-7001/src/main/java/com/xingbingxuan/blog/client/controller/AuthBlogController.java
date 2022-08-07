package com.xingbingxuan.blog.client.controller;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.service.BlogService;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 需要登录权限的blogController
 * @author : xbx
 * @date : 2022/7/19 22:21
 */
@Slf4j
@RestController
@RequestMapping("/auth/blog")
public class AuthBlogController {


    @Autowired
    private BlogService blogService;


    /**
     * 功能描述:
     * <p>根据token中用户信息获取用户的文章数</p>
     *
     * @param Authorization 请求头用户的token
     * @return : com.xingbingxuan.blog.utils.Result<java.lang.Integer>
     *
     * @author : xbx
     * @date : 2022/4/21 22:23
     */
    @GetMapping("blogCount")
    public Result<Integer> queryBlogCountByUserId(@RequestHeader String Authorization){

        //获取token = Authorization
        System.out.println(Authorization);
        //根据用户信息产询用户的文章数
        Integer count = blogService.selectBlogCountByToken(Authorization);
        return Result.success(count);
    }
    /**
     * 功能描述:
     * <p>首页，用户关注的博客</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result<java.util.List<com.xingbingxuan.blog.vo.BlogVo>>
     * @author : xbx
     * @date : 2022/6/21 22:43
     */
    @GetMapping("blogUserFollow")
    public Result<PageInfo<BlogVo>> queryBlogByUserFollow(@RequestHeader("Authorization") String token,Integer pageNum){

        PageInfo<BlogVo> blogVoPageInfo = blogService.queryBlogByUserFollow(pageNum, 10, token);

        return Result.success(blogVoPageInfo);
    }
}
