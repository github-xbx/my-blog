package com.xingbingxuan.blog.client.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.CommentEntity;
import com.xingbingxuan.blog.client.mapper.CommentMapper;
import com.xingbingxuan.blog.client.service.CommentService;
import com.xingbingxuan.blog.utils.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : xbx
 * @date : 2022/4/7 21:29
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public PageInfo<CommentEntity> allComment(Integer blogId, Integer page) {
        PageHelper.startPage(page, PAGE_SIZE);
        //获取所有的父评论 分页
        List<CommentEntity> parentList = commentMapper.selectByBidAndParentId(blogId, -1);
        //在获取所有的分评论的子评论
        List<CommentEntity> commentEntities = RecursiveGetComment(parentList);
        PageInfo<CommentEntity> pageInfo = new PageInfo<>(commentEntities);
        return pageInfo;
    }

    @Override
    public Integer queryCommentCount() {
        return commentMapper.selectCommentCount();
    }

    @Override
    public List queryCommentCountByWeek() {

        List<CommentEntity> comments = commentMapper.selectCountByWeek();

        List<String> time = DateTool.getThisWeekTime();

        List<Map<String,Object>> lists = new ArrayList<>();

        for (String s : time) {
            long count = comments.stream().filter(comment -> {
                String createTime = DateTool.DateToString("yyyy-MM-dd", comment.getCommentTime());
                return createTime.equals(s);
            }).count();
            lists.add(new HashMap<String,Object>(){{
                put("date",s);
                put("count",count);
            }});
        }

        return lists;
    }

    /**
     * 功能描述:
     * <p> 循环、递归 的方式获取所有的评论和评论的回复</p>
     *
     * @param list   根评论list
     * @return : 返回评论的所有信息 List形式
     * @author : xbx
     * @date : 2022/4/7 22:47
     */
    public List<CommentEntity> RecursiveGetComment(List<CommentEntity> list) {

        list.forEach(comment -> {
            comment.setChildren(
                    RecursiveGetComment(
                            commentMapper.selectByBidAndParentId(comment.getCommentBid(), comment.getCommentId())
                    )
            );
        });

        return list;
    }
}
