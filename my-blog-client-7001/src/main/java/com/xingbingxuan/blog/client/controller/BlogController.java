package com.xingbingxuan.blog.client.controller;


import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.BlogEntity;
import com.xingbingxuan.blog.client.entity.vo.BlogVo;
import com.xingbingxuan.blog.client.service.BlogService;
import com.xingbingxuan.blog.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : xbx
 * @date : 2022/3/24 15:18
 */
@RestController
@Slf4j
@RequestMapping("blogClient")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/test")
    public Result test(){
        return Result.success();
    }

    /**
     * 功能描述:
     * <p>分页产询所有的博客信息</p>
     *
     * @param page
     * @return : com.xingbingxuan.blog.utils.Result<com.github.pagehelper.PageInfo<com.xingbingxuan.blog.client.entity.vo.BlogVo>>
     * @author : xbx
     * @date : 2022/5/15 10:21
     */
    @RequestMapping("/getBlogPage/{page}")
    public Result<PageInfo<BlogVo>> getBlogPage(@PathVariable("page") Integer page){

        PageInfo<BlogVo> select = blogService.blogPage(page, 8);
        return Result.success(select);
    }

    @PostMapping("article")
    public Result<BlogEntity> blogArticle(@RequestBody Integer id){

        BlogEntity blogEntity = blogService.selectBlogArticle(id);
        log.info(blogEntity.toString());
        return Result.success(blogEntity);
    }

    @PostMapping("/updateBlog")
    public Result updateBlog(@RequestBody @Valid  BlogEntity blogUpdate){
        log.info(blogUpdate.toString());
        Boolean update = blogService.update(blogUpdate);
        if (update){
            return Result.success();
        }else {
            return Result.error("更改错误！！！");
        }

    }

    @PostMapping("/deleteBlog")
    public Result deleteBlog(@RequestBody Integer id){
        Boolean deleteBlog = blogService.deleteBlog(id);
        if (deleteBlog){
            return Result.success();
        }else {
            return Result.error("删除错误，没有删除成功");
        }

    }

    @PostMapping("/insertBlog")
    public Result insertBlog(@RequestBody  @Valid BlogEntity blogInsert){

        log.info(blogInsert.toString());
        Boolean addBlog = blogService.addBlog(blogInsert);
        if (true){
            return Result.success();
        }else {
            return Result.error("添加失败！！");
        }

    }

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
     * <p>查询所有博客的个数</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result<java.lang.Integer>
     * @author : xbx
     * @date : 2022/5/14 22:46
     */
    @GetMapping("adminBlogCount")
    public Result<Integer> queryBlogCount(){

        Integer count = blogService.queryBlogCount();
        return Result.success(count);
    }

    /**
     * 功能描述:
     * <p>获取最近一周的博客数量</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/5/14 23:18
     */
    @GetMapping("queryBlogCountByWeek")
    public Result queryBlogCountByWeek(){

        List list = blogService.queryBlogCountByWeek();

        return Result.success(list);
    }
}
