package com.xingbingxuan.blog.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xingbingxuan.blog.admin.fenign.BlogFeignService;
import com.xingbingxuan.blog.admin.fenign.CommentFeignService;
import com.xingbingxuan.blog.admin.fenign.UserFeignService;
import com.xingbingxuan.blog.admin.service.AdminService;
import com.xingbingxuan.blog.utils.RedisUtil;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : xbx
 * @date : 2022/5/4 22:03
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BlogFeignService blogFeignService;
    @Autowired
    private CommentFeignService commentFeignService;
    @Autowired
    private UserFeignService userFeignService;

    @Override
    public JSON blogAndUserCount() {
        JSONObject json  = new JSONObject();
        Result<Integer> blogCount = blogFeignService.queryBlogCount();
        if (blogCount.getCode() == 200){
            json.put("blogCount",blogCount.getObject());
        }else {
            json.put("blogCount",0);
        }
        Result<Integer> accountCount = userFeignService.queryAccountCount();
        if (accountCount.getCode() == 200){
            json.put("accountCount",accountCount.getObject());
        }else {
            json.put("accountCount",0);
        }
        Result<Integer> commentCount = commentFeignService.queryCommentCount();
        if (commentCount.getCode() == 200){
            json.put("commentCount",commentCount.getObject());
        }else {
            json.put("commentCount",0);
        }

        Object o = RedisUtil.get("blog:allReadCount");
        Integer integer = Integer.parseInt((String) o);
        json.put("readCount",integer);

//        ArrayList<Object> objects = new ArrayList<Object>() {{
//            add(new ArrayList<Object>() {{
//                add(new HashMap<String, String>() {{
//                    put("date", "2022/05/13");
//                    put("count", "1");
//                    put("name","注册");
//                }});
//            }});
//            add(new ArrayList<Object>() {{
//                add(new HashMap<String, String>() {{
//                    put("date", "2022/05/13");
//                    put("count", "2");
//                    put("name","留言");
//                }});
//            }});
////            add( new ArrayList<Object>() {{
////                add(new HashMap<String, String>() {{
////                    put("date", "2022/05/13");
////                    put("count", "3");
////                    put("name","回复");
////                }});
////            }}) ;
//
//          add(new ArrayList<Object>() {{
//              add(new HashMap<String, String >() {{
//                  put("date", "2022/05/13");
//                  put("count", "4");
//                  put("name","文章");
//              }});
//          }});
//
//        }};



        return json;
    }

    @Override
    public List getChartData() {

        Result user = userFeignService.accountByWeek();
        Result blog = blogFeignService.queryBlogCountByWeek();
        Result comment = commentFeignService.queryCommentCountByWeek();

        //JSONObject json = new JSONObject();
        List list = new ArrayList();
        if (comment.getCode().equals(200)){
            list.add(comment.getObject());
        }
        if (blog.getCode().equals(200)){
            list.add(blog.getObject());
        }
        if (user.getCode().equals(200)){
            list.add(user.getObject());
        }




        //System.out.println();
        return list;
    }
}
