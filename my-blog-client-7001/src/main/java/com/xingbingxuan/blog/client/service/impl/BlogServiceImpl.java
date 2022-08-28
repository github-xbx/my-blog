package com.xingbingxuan.blog.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xingbingxuan.blog.client.entity.BlogEntity;
import com.xingbingxuan.blog.client.entity.BlogSetEntity;
import com.xingbingxuan.blog.client.entity.CategoryEntity;
import com.xingbingxuan.blog.client.feign.BlogUserFeignService;

import com.xingbingxuan.blog.client.mapper.BlogMapper;
import com.xingbingxuan.blog.client.mapper.BlogSetMapper;
import com.xingbingxuan.blog.client.mapper.CategoryMapper;
import com.xingbingxuan.blog.client.mapper.LabelMapper;
import com.xingbingxuan.blog.client.service.BlogService;
import com.xingbingxuan.blog.database.RedisEntity;
import com.xingbingxuan.blog.utils.DateTool;
import com.xingbingxuan.blog.utils.RedisUtil;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.utils.TokenUtil;
import com.xingbingxuan.blog.vo.BlogVo;
import com.xingbingxuan.blog.vo.LabelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : xbx
 * @date : 2022/4/4 11:25
 */
@Service
@Slf4j
public class BlogServiceImpl implements BlogService {


    private static final String BLOG_READ_COUNT_PREFIX = "blog:readCount:";
    private static final String BLOG_READ_COUNT_REDIS_KEY = BLOG_READ_COUNT_PREFIX+"*";
    private static final Integer PAGE_SIZE = 10;

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BlogSetMapper blogSetMapper;
    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private BlogUserFeignService userFeignService;



    @Override
    public PageInfo<BlogEntity> select(int pageNum,int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<BlogEntity> list = blogMapper.selectBlog();
        PageInfo<BlogEntity> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public PageInfo<BlogVo> blogPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<BlogVo> blogVos = blogMapper.selectAllBlogAndLabel();
        blogVos.forEach(blogVo -> {
            CategoryEntity categoryEntity = categoryMapper.selectAllByBId(blogVo.getBlogId());
            BeanUtils.copyProperties(categoryEntity,blogVo);
        });

        PageInfo<BlogVo> blogAndSeriesPageInfo = new PageInfo<>(blogVos);
        return blogAndSeriesPageInfo;
    }

    @Override
    public BlogEntity selectBlogArticle(Integer id) {
        return blogMapper.selectAllById(id);
    }

    @Override
    public Integer selectBlogCountByToken(String token) {

        //获取token中的用户数据
        JSONObject user = JSON.parseObject(TokenUtil.getUserByToken(token));

        return blogMapper.selectBlogCount((Integer) user.get("id"));
    }

    @Override
    public Integer queryBlogCount() {


        return blogMapper.selectBlogCount(null);
    }

    @Override
    public Boolean addBlog(BlogEntity blogEntity) {
        Integer insertBlog = blogMapper.insertBlog(blogEntity);
        return insertBlog > 0;
    }

    @Override
    public Boolean deleteBlog(Integer id) {
        Integer deleteBlog = blogMapper.deleteBlogByBlogId(id);
        //System.out.println(deleteBlog);
        return deleteBlog > 0 ;
    }

    @Override
    public Boolean update(BlogEntity blogEntity) {
        Integer updateBlog = blogMapper.updateBlog(blogEntity);
        return updateBlog > 0;
    }

    @Override
    public List queryBlogCountByWeek() {

        List<BlogEntity> blogs = blogMapper.selectCountByWeek();

        List<String> time = DateTool.getThisWeekTime();

        List<Map<String,Object>> lists = new ArrayList<>();

        for (String s : time) {

            long count = blogs.stream().filter(blog -> {
                String createTime = DateTool.DateToString("yyyy-MM-dd", blog.getBInsertTime());
                return createTime.equals(s);
            }).count();
            lists.add(new HashMap<String,Object>(){{
                put("date",s);
                put("count",count);
            }});

        }

        return lists;


    }

    @Override
    public Map<Integer, List<BlogVo>> queryBlogByIndexRecommend() {

        //查询推荐的博客
        List<BlogSetEntity> blogSets = blogSetMapper.selectAll();

        List<Integer> settings = blogSets.stream().map(BlogSetEntity::getIndexSetting).collect(Collectors.toList());

        List<Integer> blogIds = blogSets.stream().map(blogSetEntity -> blogSetEntity.getBlogId()).collect(Collectors.toList());
        //获取博客信息
        List<BlogVo> blogVos = blogMapper.selectAllByBlogIds(blogIds);




        //根据博客信息获取用户头像
        Map<Integer, Integer> map = blogVos.stream().collect(Collectors.toMap(BlogVo::getBlogId, BlogVo::getBlogUid));

        log.info("account服务传的参数"+map);
        Map header = userFeignService.queryUserHeaderByIds(map);



        Map<Integer, List<Integer>> setBlogIdMap = blogSets.stream().collect(Collectors.toMap(
                BlogSetEntity::getIndexSetting,
                t -> Lists.newArrayList(t.getBlogId()),
                (List<Integer> oldList, List<Integer> newList) -> {
                    oldList.addAll(newList);
                    return oldList;
                })
        );

        Map<Integer, List<BlogVo>> result = new HashMap<>();

        settings.forEach(setting ->{
            List<Integer> list = setBlogIdMap.get(setting);
            List<BlogVo> vos = new ArrayList<>();
            blogVos.stream().forEach(blogVo -> {
                if (list.contains(blogVo.getBlogId())){
                    blogVo.setIndexSetting(setting);
                    blogVo.setUserHeader((String) header.get(blogVo.getBlogId().toString()));


                    vos.add(blogVo);
                }
            });
            result.put(setting,vos);
        });


        //Map<Integer, BlogSetEntity> setEntityMap = blogSets.stream().collect(Collectors.toMap(BlogSetEntity::getBlogId, Function.identity()));




//        //组装BlogVo返回
//        blogVos.stream().forEach(blogVo -> {
//            BeanUtils.copyProperties(setEntityMap.get(blogVo.getBlogId()),blogVo);
//            blogVo.setUserHeader((String) header.get(blogVo.getBlogId().toString()));
//            List<LabelVo> labels = labelMapper.selectAllByBlogId(blogVo.getBlogId());
//            blogVo.setLabel(labels);
//        });

        return result;
    }

    @Override
    public PageInfo<BlogVo> queryBlogByUserFollow(int pageNum, int pageSize,String token) {

        PageHelper.startPage(pageNum,pageSize);

        //解析token
        String userId = TokenUtil.getObjectByToken(Base64.getDecoder().decode(token));

        //获取关注的用户id
        Result<List<Integer>> userFollow = userFeignService.queryUserFollowUserId(Integer.valueOf(userId));
        List<Integer> userIds;
        if (userFollow.getCode().equals(200) ){
            userIds = userFollow.getObject();
        }else {
            return new PageInfo<>();
        }

        //根据关注的用户id 查询博客并根据时间排序
        List<BlogVo> blogVos = blogMapper.selectAllByUserIds(userIds);


        return new PageInfo<>(blogVos);
    }

    @Override
    public PageInfo<BlogVo> queryIndexBlogList(Integer pageNo) {

        PageHelper.startPage(pageNo,PAGE_SIZE);

        List<BlogSetEntity> blogSet = blogSetMapper.selectAllBySetting(3);

        List<Integer> blogIds = blogSet.stream().map(BlogSetEntity::getBlogId).collect(Collectors.toList());

        List<BlogVo> blogVos = blogMapper.selectAllByBlogIds(blogIds);

        //获取博客用户的头像信息
        blogVos = this.getUserHeader(blogVos);
        //获取博客阅读的次数
        this.blogReadCount(blogVos);


        PageInfo<BlogVo> result = new PageInfo<>(blogVos);

        return result;
    }

    @Override
    public PageInfo<BlogVo> queryIndexNew(Integer pageNo) {

        PageHelper.startPage(pageNo,PAGE_SIZE);

        List<BlogVo> blogVos = blogMapper.selectAllOrderByTime();

        //获取博客用户的头像信息
        blogVos = this.getUserHeader(blogVos);
        //获取博客阅读的次数
        this.blogReadCount(blogVos);

        return new PageInfo<>(blogVos);
    }

    @Override
    public PageInfo<BlogVo> queryIndexHot(Integer pageNo) {

        PageHelper.startPage(pageNo,PAGE_SIZE);

        //获取所有的keys
        Set<String> keys = RedisUtil.keys(BLOG_READ_COUNT_REDIS_KEY);


        //根据redis的key 获取blogId
        List<Integer> blogIds = keys.stream().map(a -> a.substring(a.indexOf(":", a.indexOf(":") + 1) + 1)).map(Integer::valueOf).collect(Collectors.toList());


        //获取blog信息
        List<BlogVo> blogVos = blogMapper.selectAllByBlogIds(blogIds);
        //获取博客用户的头像信息
        blogVos = this.getUserHeader(blogVos);
        //获取博客阅读的次数
        this.blogReadCount(blogVos);

        //blogVos 根据readCount排序
        blogVos.sort((a,b) ->{
            return b.getReadCount() - a.getReadCount();
        });


        return new PageInfo<>(blogVos);
    }



    /**
     * 功能描述:
     * <p>blog user header image </p>
     *
     * @param blogVos
     * @return : java.util.List<com.xingbingxuan.blog.vo.BlogVo>
     * @author : xbx
     * @date : 2022/8/28 17:00
     */
    private List<BlogVo> getUserHeader(List<BlogVo> blogVos){
        //根据博客信息获取用户头像
        Map<Integer, Integer> map = blogVos.stream().collect(Collectors.toMap(BlogVo::getBlogId, BlogVo::getBlogUid));

        log.info("account服务传的参数"+map);
        Map header = userFeignService.queryUserHeaderByIds(map);
        //log.info("调试日志->"+header);
        for (BlogVo blogVo : blogVos) {
            //log.info("调试日志->"+blogVo.getBlogUid().toString());

            blogVo.setUserHeader(String.valueOf(header.get(blogVo.getBlogId().toString())));
            //log.info("调试日志->"+header.get(blogVo.getBlogId().toString()));
        }
        return blogVos;

    }

    /**
     * 功能描述:
     * <p>blog 阅读次数</p>
     *
     * @param blogVos
     * @return : void
     * @author : xbx
     * @date : 2022/8/28 18:39
     */
    private void blogReadCount(List<BlogVo> blogVos){

        blogVos.forEach(a -> {
            Integer blogId = a.getBlogId();
            Object value = RedisUtil.get(BLOG_READ_COUNT_PREFIX + blogId.toString());

            Integer blogReadCount = value == null ? 0 :Integer.parseInt(value.toString());

            a.setReadCount(blogReadCount == null ? 0 : blogReadCount );
        });

    }
}
