package com.xingbingxuan.blog.client.controller;


import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.BlogEntity;
import com.xingbingxuan.blog.client.service.BlogService;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
     * <p>查询所有博客的个数</p>
     * <p>管理员端，统计个数</p>
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
     * <p>管理员端，统计个数</p>
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/5/14 23:18
     */
    @GetMapping("queryBlogCountByWeek")
    public Result queryBlogCountByWeek(){

        List list = blogService.queryBlogCountByWeek();

        return Result.success(list);
    }

    /**
     * 功能描述:
     * <p>首页，获取所有的推荐博客信息</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/6/12 11:40
     */
    @GetMapping("queryBlogByIndexRecommend")
    public Result<Map<Integer, List<BlogVo>>> queryBlogByIndexRecommend(){

        Map<Integer, List<BlogVo>> map = blogService.queryBlogByIndexRecommend();

        return Result.success(map);
    }

    /**
     * 功能描述:
     * <p>首页标签切换方法</p>
     *
     * @param tabId
     * @return : com.xingbingxuan.blog.utils.Result<com.github.pagehelper.PageInfo<com.xingbingxuan.blog.vo.BlogVo>>
     * @author : xbx
     * @date : 2022/8/14 19:33
     */
    @GetMapping("queryByTabId/{tabId}/{pageNo}")
    public Result<PageInfo<BlogVo>> queryByTabId(@PathVariable Integer tabId,@PathVariable Integer pageNo){

        PageInfo<BlogVo> result = null;

        switch (tabId){
            case 1:
                //推荐
                result = this.blogService.queryIndexBlogList(pageNo);
                break;
            case 2:
                //最新
                result = this.blogService.queryIndexNew(pageNo);
                break;
            case 3:
                //最热
                result = this.blogService.queryIndexHot(pageNo);
                break;
            default:
                //都没有
                return Result.error(500,"没有选项编号");

        }

        return Result.success(result);
    }

}
