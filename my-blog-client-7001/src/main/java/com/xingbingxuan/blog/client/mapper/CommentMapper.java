package com.xingbingxuan.blog.client.mapper;

import com.xingbingxuan.blog.client.entity.CommentEntity;
import com.xingbingxuan.blog.client.entity.vo.CommentVo;
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
     * <p>根据父id查找所有的一级子信息</p>
     *
     * @param parentId
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.vo.CommentVo>
     * @author : xbx
     * @date : 2022/5/22 22:47
     */
    public List<CommentVo> selectAllByCommentParentId(Integer parentId);

    /**
     * 功能描述:
     * <p>查询最近一周的留言</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.CommentEntity>
     * @author : xbx
     * @date : 2022/5/14 23:09
     */
    public List<CommentEntity> selectCountByWeek();

    /**
     * 功能描述:
     * <p>查询所有的评论信息</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.vo.CommentVo>
     * @author : xbx
     * @date : 2022/5/16 22:28
     */
    public List<CommentVo> selectAllComment(@Param("search") String search);


    /**
     * 功能描述:
     * <p>根据具体的字段更改comment评论留言信息</p>
     * <p>不为空的字段都会更改</p>
     * <p>目前只能改内容和状态</p>
     * @param commentEntity
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/5/21 11:52
     */
    public Integer updateCommentBy(CommentEntity commentEntity);
}
