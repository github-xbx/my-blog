package com.xingbingxuan.blog.client.service;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.BlogEntity;

import com.xingbingxuan.blog.client.entity.vo.BlogVo;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/4 11:25
 */
public interface BlogService {

    public PageInfo<BlogEntity> select(int pageNum,int pageSize);

    public PageInfo<BlogVo> blogPage(int pageNum, int pageSize);

    public BlogEntity selectBlogArticle(Integer id);

    Integer selectBlogCountByToken(String token);

    Integer queryBlogCount();

    public Boolean addBlog(BlogEntity blogEntity);

    public Boolean deleteBlog(Integer id);

    public Boolean update(BlogEntity blogEntity);

    /**
     * 功能描述:
     * <p>查询最近一周的新增博客数量</p>
     *
     * @return : java.util.List
     * @author : xbx
     * @date : 2022/5/14 23:13
     */
    public List queryBlogCountByWeek();

}
