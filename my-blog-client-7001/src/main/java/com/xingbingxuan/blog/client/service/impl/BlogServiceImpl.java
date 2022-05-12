package com.xingbingxuan.blog.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.BlogEntity;
import com.xingbingxuan.blog.client.entity.SeriesEntity;
import com.xingbingxuan.blog.client.entity.and.BlogAndSeries;
import com.xingbingxuan.blog.client.entity.vo.BlogVo;
import com.xingbingxuan.blog.client.mapper.BlogMapper;
import com.xingbingxuan.blog.client.mapper.SeriesMapper;
import com.xingbingxuan.blog.client.service.BlogService;
import com.xingbingxuan.blog.database.Page;
import com.xingbingxuan.blog.utils.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/4 11:25
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private SeriesMapper seriesMapper;

    @Override
    public PageInfo<BlogEntity> select(int pageNum,int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<BlogEntity> list = blogMapper.selectBlog();
        PageInfo<BlogEntity> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public PageInfo<BlogVo> blogPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<BlogVo> blogVos = blogMapper.selectAllBlogAndLabel();
        blogVos.forEach(blogVo -> {
            SeriesEntity seriesEntity = seriesMapper.selectAllByBId(blogVo.getBlogId());
            BeanUtils.copyProperties(seriesEntity,blogVo);
        });

        PageInfo<BlogVo> blogAndSeriesPageInfo = new PageInfo<>(blogVos);
        return blogAndSeriesPageInfo;
    }

    @Override
    public BlogEntity selectBlogArticle(Integer id) {
        return blogMapper.selectAllById(id);
    }

    @Override
    public Integer selectBlogCountByToken(String token) {

        //获取token中的用户数据
        JSONObject user = JSON.parseObject(TokenUtil.getUserByToken(token));

        return blogMapper.selectBlogCount((Integer) user.get("id"));
    }

    @Override
    public Integer queryBlogCount() {


        return blogMapper.selectBlogCount(null);
    }

    @Override
    public Boolean addBlog(BlogEntity blogEntity) {
        Integer insertBlog = blogMapper.insertBlog(blogEntity);
        return insertBlog > 0;
    }

    @Override
    public Boolean deleteBlog(Integer id) {
        Integer deleteBlog = blogMapper.deleteBlogByBlogId(id);
        //System.out.println(deleteBlog);
        return deleteBlog > 0 ;
    }

    @Override
    public Boolean update(BlogEntity blogEntity) {
        Integer updateBlog = blogMapper.updateBlog(blogEntity);
        return updateBlog > 0;
    }
}
