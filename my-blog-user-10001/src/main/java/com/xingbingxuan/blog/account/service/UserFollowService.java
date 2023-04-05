package com.xingbingxuan.blog.account.service;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/6/20 23:22
 */
public interface UserFollowService {

    /**
     * 功能描述:
     * <p>根据用户id查询，用户关注的用户id</p>
     *
     * @param userId
     * @return : java.util.List<java.lang.Integer>
     * @author : xbx
     * @date : 2022/6/20 23:23
     */
    public List<Integer> queryUserFollowIdByUserId(Integer userId);
}
