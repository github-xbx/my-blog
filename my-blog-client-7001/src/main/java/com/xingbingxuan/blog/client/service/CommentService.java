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
     * <p>分页查询</p>
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return : java.util.List<com.xingbingxuan.blog.client.entity.vo.CommentVo>
     * @author : xbx
     * @date : 2022/5/16 22:23
     */
    public PageInfo<CommentVo> queryAllCommentPage(String search,Integer pageNum,Integer pageSize);
}
