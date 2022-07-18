package com.xingbingxuan.blog.client.service;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.CommentEntity;
import com.xingbingxuan.blog.client.entity.vo.CommentVo;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/7 21:28
 */
public interface CommentService {

    public PageInfo<CommentEntity> allComment(Integer BlogId, Integer page);

    public Integer queryCommentCount();

    /**
     * 功能描述:
     * <p>获取最近一周评论的个数</p>
     *
     * @return : java.util.List
     * @author : xbx
     * @date : 2022/5/14 23:20
     */
    public List queryCommentCountByWeek();

    /**
     * 功能描述:
     * <p>管理员 所有父级标签分页查询</p>
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.vo.CommentVo>
     * @author : xbx
     * @date : 2022/5/16 22:23
     */
    public PageInfo<CommentVo> queryAllCommentPage(String search,Integer pageNum,Integer pageSize);

    /**
     * 功能描述:
     * <p>查询子集评论</p>
     * @param parentId 附近评论id
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.vo.CommentVo>
     * @author : xbx
     * @date : 2022/5/29 10:16
     */
    public List<CommentVo> queryAllCommentChild(Integer parentId);

    /**
     * 功能描述:
     * <p>根据评论的实体信息更改</p>
     *
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/5/21 15:56
     */
    public Integer updateCommentByEntity(CommentEntity commentEntity) throws Exception;
}
