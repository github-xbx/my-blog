package com.xingbingxuan.blog.admin.service;

import com.alibaba.fastjson.JSON;
import com.xingbingxuan.blog.utils.Result;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/5/4 22:00
 */
public interface AdminService {

    /**
     * 功能描述:
     * <p>获取博客、用户、评论、阅读的数量</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result
     * @author : xbx
     * @date : 2022/5/4 22:01
     */
    public JSON blogAndUserCount();

    /**
     * 功能描述:
     * <p>获取图表所需要的数据</p>
     *
     * @return : com.alibaba.fastjson.JSON
     * @author : xbx
     * @date : 2022/5/14 22:42
     */
    public List getChartData();
}
