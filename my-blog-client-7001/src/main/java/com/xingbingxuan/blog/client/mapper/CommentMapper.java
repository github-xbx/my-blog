package com.xingbingxuan.blog.client.mapper;

import com.xingbingxuan.blog.client.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论表mapper
 * @author : xbx
 * @date : 2022/4/7 16:41
 */
@Mapper
public interface CommentMapper {

    public List<CommentEntity> selectByBid(@Param("blogId") Integer blogId);

    public List<CommentEntity> selectByBidAndParentId(@Param("commentBid") Integer commentBid
            ,@Param("commentParentId") Integer commentParentId);

    /**
     * 功能描述:
     * <p>获取所有留言评论的个数</p>
     *
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/5/4 16:39
     */
    public Integer selectCommentCount();

    /**
     * 功能描述:
     * <p>查询最近一周的留言</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.CommentEntity>
     * @author : xbx
     * @date : 2022/5/14 23:09
     */
    public List<CommentEntity> selectCountByWeek();
}
