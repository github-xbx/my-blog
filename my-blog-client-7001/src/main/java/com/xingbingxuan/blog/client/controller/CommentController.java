package com.xingbingxuan.blog.client.controller;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.CommentEntity;
import com.xingbingxuan.blog.client.entity.vo.CommentVo;
import com.xingbingxuan.blog.client.service.CommentService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /**
     * 功能描述:
     * <p>获取最近一周评论的个数</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/5/14 23:19
     */
    @GetMapping("queryCommentCountByWeek")
    public Result queryCommentCountByWeek(){
        List list = commentService.queryCommentCountByWeek();

        return Result.success(list);
    }


    /**
     * 功能描述:
     * <p>分页查询所有的评论信息</p>
     *
     * @param map
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/5/16 22:18
     */
    @PostMapping("queryAllCommentPage")
    public Result queryAllCommentPage(@RequestBody Map map){


        PageInfo<CommentVo> pageInfo = commentService.queryAllCommentPage(
                        (String) map.get("search"), (Integer) map.get("pageNo"), (Integer) map.get("pageSize"));

        return Result.success(pageInfo);
    }
}
