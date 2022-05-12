package com.xingbingxuan.blog.client.mapper;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.BlogEntity;
import com.xingbingxuan.blog.client.entity.and.BlogAndSeries;
import com.xingbingxuan.blog.client.entity.vo.BlogVo;
import com.xingbingxuan.blog.database.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/4 9:27
 */
@Mapper
public interface BlogMapper {

    /**
     * 功能描述:
     * <p>插入一个新的博客内容</p>
     *
     * @param blogEntity 插入的实体类对象
     * @return : 返回nysql的插入返回值 <0 为失败
     *
     * @author : xbx
     * @date : 2022/4/4 9:55
     */
    public Integer insertBlog(BlogEntity blogEntity);

    /**
     * 功能描述:
     * <p>根据blogId 删除 博客</p>
     *
     * @param blogId 要删除的博客id
     * @return : 返回值，<0 操作失败，>0 操作成功
     *
     * @author : xbx
     * @date : 2022/4/4 9:58
     */
    public Integer deleteBlogByBlogId(Integer blogId);

    /**
     * 功能描述:
     * <p>更改</p>
     *
     * @param blogEntity 要更改的对象
     * @return : 返回值，<0 操作失败，>0 操作成功
     *
     * @author : xbx
     * @date : 2022/4/4 10:02
     */
    public Integer updateBlog(BlogEntity blogEntity);

    /**
     * 功能描述:
     * <p>查询所有</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.BlogEntity>
     *
     * @author : xbx
     * @date : 2022/4/4 10:03
     */
    public List<BlogEntity> selectBlog();

    /**
     * 功能描述:
     * <p>查询最新的10条博客数据</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.BlogEntity>
     *
     * @author : xbx
     * @date : 2022/4/6 17:20
     */
    public List<BlogEntity> selectBlogName();

    /**
     * 功能描述:
     * <p>根据博客的id查询博客的所有信息</p>
     *
     * @param blogId 博客id
     * @return : com.xingbingxuan.blog.client.entity.BlogEntity
     *
     * @author : xbx
     * @date : 2022/4/6 17:21
     */
    public BlogEntity selectAllById(Integer blogId);

    /**
     * 功能描述:
     * <p>查询博客的所有信息包括标签</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.and.BlogAndSeries>
     *
     * @author : xbx
     * @date : 2022/4/6 17:25
     */
    public List<BlogVo> selectAllBlogAndSeries();

    /**
     * 功能描述:
     * <p>根据用户的id产询用户名下的blog的个数</p>
     *
     * @param uid
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/5/2 9:56
     */
    public Integer selectBlogCount(Integer uid);

    /**
     * 功能描述:
     * <p>查询博客和它的标签</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.vo.BlogVo>
     * @author : xbx
     * @date : 2022/5/3 11:51
     */
    public List<BlogVo> selectAllBlogAndLabel();

}
