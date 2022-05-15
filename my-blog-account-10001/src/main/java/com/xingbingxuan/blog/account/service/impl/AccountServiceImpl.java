package com.xingbingxuan.blog.account.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.mapper.AccountMapper;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.utils.DateTool;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : xbx
 * @date : 2022/3/25 20:15
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public PageInfo<UserEntity> queryAllUserPage(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<UserEntity> users = accountMapper.selectAllUser();

        PageInfo<UserEntity> pageInfo = new PageInfo<>(users);


        return pageInfo;
    }

    @Override
    public UserEntity selectOneByUsernameAndSocialUid(UserEntity userEntity) {

        return accountMapper.selectOneAnd(userEntity);
    }

    @Override
    public Integer addAccount(UserEntity userEntity) {
        return 1;
    }

    @Override
    public UserEntity giteeLogin(String token) {

        //根据accessToken获取gitee账户的基本信息
        HttpResponse accessTokenResponse = HttpRequest.get("https://gitee.com/api/v5/user")
                .form("access_token", token)
                .execute();
        //初始化一个对象用于返回
        UserEntity user = null;

        if (accessTokenResponse.getStatus() == 200) {
            JSONObject userInfoJson = JSONUtil.parseObj(accessTokenResponse.body());
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("gitee_" + userInfoJson.get("name").toString());
            userEntity.setSocialType("gitee");
            userEntity.setSocialUid(userInfoJson.get("id").toString());

            //根据信息产询账户表中是否存在该用户
            user = accountMapper.selectOneAnd(userEntity);
            if (user == null) {
                //账户不存在，注册
                userEntity.setCreateTime(Calendar.getInstance().getTime());
                userEntity.setPassword(bCryptPasswordEncoder.encode("00000000"));
                userEntity.setHeader(userInfoJson.get("avatar_url").toString());
                Integer integer = accountMapper.insertAccount(userEntity);
                if (integer > 0) {
                    //添加成功，注册成功
                    userEntity.setPassword(null);
                    user = userEntity;
                }
            }else {
                //存在
            }
        }
        return user;
    }

    @Override
    public Integer queryAccountCount() {
        return accountMapper.selectAccountCount();
    }

    @Override
    public List queryAccountCountByThisWeek() {

        List<UserEntity> users = accountMapper.selectByThisWeek();

        List<String> time = DateTool.getThisWeekTime();

        List<Map<String,Object>> lists = new ArrayList<>();

        for (String s : time) {
            long count = users.stream().filter(user -> {
                String createTime = DateTool.DateToString("yyyy-MM-dd", user.getCreateTime());
                return createTime.equals(s);
            }).count();

            lists.add(new HashMap<String,Object>(){{
                put("date",s);
                put("count",count);
            }});
        }


        return lists;
    }
}
