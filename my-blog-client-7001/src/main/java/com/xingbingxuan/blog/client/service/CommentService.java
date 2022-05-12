package com.xingbingxuan.blog.client.service;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.CommentEntity;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/7 21:28
 */
public interface CommentService {

    public PageInfo<CommentEntity> allComment(Integer BlogId, Integer page);

    public Integer queryCommentCount();
}
