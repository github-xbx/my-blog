package com.xingbingxuan.blog.client.mapper;


import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.BlogEntity;
import com.xingbingxuan.blog.vo.BlogVo;
import com.xingbingxuan.blog.vo.LabelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 功能描述:
     * <p>查询最近一周新增的博客</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.BlogEntity>
     * @author : xbx
     * @date : 2022/5/14 23:06
     */
    public List<BlogEntity> selectCountByWeek();

    /**
     * 功能描述:
     * <p>根据博客的id集合产询出博客所有的信息</p>
     *
     * @param blogIds
     * @return : java.util.List<com.xingbingxuan.blog.vo.BlogVo>
     * @author : xbx
     * @date : 2022/8/4 23:19
     */
    public List<BlogVo> selectAllByBlogIds(List<Integer> blogIds);


    /**
     * 功能描述:
     * <p>根据用户的id分页查询出用户的所有博客信息</p>
     *
     * @param userIds
     * @return : java.util.List<com.xingbingxuan.blog.vo.BlogVo>
     * @author : xbx
     * @date : 2022/8/6 17:20
     */
    public List<BlogVo> selectAllByUserIds(List<Integer> userIds);

    /**
     * 功能描述:
     * <p>获取所有博客信息（包括关联的标签、类型等信息）根据时间排序</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.vo.BlogVo>
     * @author : xbx
     * @date : 2022/8/14 21:50
     */
    public List<BlogVo> selectAllOrderByTime();

}
