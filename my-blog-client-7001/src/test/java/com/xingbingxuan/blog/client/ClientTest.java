package com.xingbingxuan.blog.client;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.client.entity.BlogEntity;
import com.xingbingxuan.blog.client.entity.CommentEntity;
import com.xingbingxuan.blog.client.entity.LabelEntity;
import com.xingbingxuan.blog.client.entity.SeriesEntity;
import com.xingbingxuan.blog.client.entity.and.BlogAndSeries;
import com.xingbingxuan.blog.client.mapper.BlogMapper;
import com.xingbingxuan.blog.client.mapper.CommentMapper;
import com.xingbingxuan.blog.client.mapper.LabelMapper;
import com.xingbingxuan.blog.client.mapper.SeriesMapper;
import com.xingbingxuan.blog.client.service.BlogService;
import com.xingbingxuan.blog.client.service.CommentService;
import com.xingbingxuan.blog.database.Page;
import com.xingbingxuan.blog.utils.RedisUtil;
import com.xingbingxuan.blog.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * @author : xbx
 * @date : 2022/4/4 11:40
 */
@SpringBootTest
@Slf4j
public class ClientTest {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private SeriesMapper seriesMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;


    @Test
    public void test005() {
        Set<String> keys = RedisUtil.keys("blog:readCount:*");

        keys.forEach(a -> {
            String substring = a.substring(a.indexOf(":", a.indexOf(":")+1)+1);
            System.out.println(substring);
        });
    }


    @Test
    public void test004(){
        Integer integer = blogMapper.deleteBlogByBlogId(4);
        if (integer > 0) log.info("删除成功。。。");
    }

    @Test
    public void test003(){
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setBContent("uodate");
        blogEntity.setBDescription("update");
        blogEntity.setBImg("update");
        blogEntity.setBInsertTime(Calendar.getInstance().getTime());
        blogEntity.setBTitle("update");
        blogEntity.setBTid(2);
        blogEntity.setBUid(1212);
        blogEntity.setBState(0);
        blogEntity.setBId(4);
        Integer integer = blogMapper.updateBlog(blogEntity);
        if (integer>0)
            log.info("更改成功。。。。");
    }

    @Test
    public void test002(){
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setBContent("1212112");
        blogEntity.setBDescription("1212121");
        blogEntity.setBImg("dasdsada");
        blogEntity.setBInsertTime(Calendar.getInstance().getTime());
        blogEntity.setBTitle("2121212112121");
        blogEntity.setBTid(1);
        blogEntity.setBUid(121);
        blogEntity.setBState(1);
        Integer integer = blogMapper.insertBlog(blogEntity);
        if (integer > 0)
            log.info("插入成功。。。。");
    }


    @Test
    public void test001(){
        PageInfo<BlogEntity> select = blogService.select(1,1);
        System.out.println(select);
    }

}
