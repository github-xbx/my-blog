package com.xingbingxuan.blog.client.service;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.BlogEntity;
import com.xingbingxuan.blog.vo.BlogVo;


import java.util.List;
import java.util.Map;

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

    /**
     * 功能描述:
     * <p>获取首页推荐的博客</p>
     *
     * @return : java.util.List
     * @author : xbx
     * @date : 2022/6/16 22:35
     */
    public Map<Integer, List<BlogVo>> queryBlogByIndexRecommend();

    /**
     * 功能描述:
     * <p>首页，用户关注的博客</p>
     * 需要登录，登录返回，未登录
     * @param pageNum  页数
     * @param pageSize 页面大小
     * @return : java.util.List<com.xingbingxuan.blog.vo.BlogVo>
     * @author : xbx
     * @date : 2022/6/21 22:45
     */
    public PageInfo<BlogVo> queryBlogByUserFollow(int pageNum, int pageSize,String token);

    /**
     * 功能描述:
     * <p>获取首页推荐的博客信息（分页）</p>
     *
     * @return : com.github.pagehelper.PageInfo<com.xingbingxuan.blog.client.entity.vo.BlogVo>
     * @author : xbx
     * @date : 2022/8/14 21:35
     */
    public PageInfo<BlogVo> queryIndexBlogList(Integer pageNo);

    /**
     * 功能描述:
     * <p>获取最新的博客信息（）分页</p>
     *
     * @return : com.github.pagehelper.PageInfo<com.xingbingxuan.blog.client.entity.vo.BlogVo>
     * @author : xbx
     * @date : 2022/8/14 21:36
     */
    public PageInfo<BlogVo> queryIndexNew(Integer pageNo);
    /**
     * 功能描述:
     * <p>获取最热（根据热度（点赞）最高）博客信息（分页）</p>
     *
     * @return : com.github.pagehelper.PageInfo<com.xingbingxuan.blog.client.entity.vo.BlogVo>
     * @author : xbx
     * @date : 2022/8/14 21:38
     */
    public PageInfo<BlogVo> queryIndexHot(Integer pageNo);
}
