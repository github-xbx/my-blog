package com.xingbingxuan.blog.account.service.impl;

import com.xingbingxuan.blog.account.entity.UserFollowEntity;
import com.xingbingxuan.blog.account.mapper.UserFollowMapper;
import com.xingbingxuan.blog.account.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : xbx
 * @date : 2022/6/20 23:24
 */
@Service
public class UserFollowServiceImpl implements UserFollowService {


    @Autowired
    private UserFollowMapper userFollowMapper;


    @Override
    public List<Integer> queryUserFollowIdByUserId(Integer userId) {
        List<UserFollowEntity> userFollows = this.userFollowMapper.selectAllByUserId(userId);

        List<Integer> list = userFollows.stream()
                .map(userFollowEntity -> userFollowEntity.getUserFollowId()).collect(Collectors.toList());

        return list;
    }
}
