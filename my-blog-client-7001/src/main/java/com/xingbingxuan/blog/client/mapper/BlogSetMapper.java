package com.xingbingxuan.blog.client.mapper;

import com.xingbingxuan.blog.client.entity.BlogSetEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/6/18 17:35
 */
@Mapper
public interface BlogSetMapper {


    /**
     * 功能描述:
     * <p>查询所有</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.BlogSetEntity>
     * @author : xbx
     * @date : 2022/6/18 17:36
     */
    public List<BlogSetEntity> selectAll();

    /**
     * 功能描述:
     * <p>查询所有的博客id</p>
     *
     * @return : java.util.List<java.lang.Integer>
     * @author : xbx
     * @date : 2022/6/18 17:41
     */
    public List<Integer> selectAllBlogId();
}
