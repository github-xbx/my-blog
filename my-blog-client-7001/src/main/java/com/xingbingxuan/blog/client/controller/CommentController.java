package com.xingbingxuan.blog.client.controller;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.CommentEntity;
import com.xingbingxuan.blog.client.service.CommentService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/7 22:50
 */
@RestController
@RequestMapping("blogClient")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("message/pageList/{blogId}/{page}")
    public Result<PageInfo<CommentEntity>> pageList(
            @PathVariable("blogId") Integer blogId,
            @PathVariable("page") Integer page){

        PageInfo<CommentEntity> allComment = commentService.allComment(blogId, page);
        return Result.success(allComment);
    }

    @GetMapping("message/count")
    public Result<Integer> queryCommentCount(){
        Integer count = commentService.queryCommentCount();

        return Result.success(count);
    }
}
