package com.xingbingxuan.blog.account.service;

import com.github.pagehelper.PageInfo;
import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * @author : xbx
 * @date : 2022/3/25 20:09
 */
public interface AccountService {


    /**
     * 功能描述:
     * <p>分页查询所有的用户信息</p>
     *
     * @return : com.github.pagehelper.PageInfo<com.xingbingxuan.blog.account.entity.UserEntity>
     * @author : xbx
     * @date : 2022/5/15 11:37
     */
    PageInfo<UserEntity> queryAllUserPage(Integer pageNum,Integer pageSize);

    /**
     * 功能描述:
     * <p>根据用户名和邮箱产询</p>
     *
     * @param userEntity description
     * @return : com.xingbingxuan.blog.account.entity.UserEntity
     *
     * @author : xbx
     * @date : 2022/3/25 20:13
     */
    UserEntity selectOneByUsernameAndSocialUid(UserEntity userEntity);

    /**
     * 功能描述:
     * <p>添加一个账户</p>
     *
     * @param userEntity description
     * @return : java.lang.Integer
     *
     * @author : xbx
     * @date : 2022/3/25 20:33
     */
    Integer addAccount(UserEntity userEntity);
    /**
     * 功能描述:
     * <p>第三方 gitee 登录</p>
     *
     * @param token 第三方登录的token
     * @return : com.xingbingxuan.blog.account.entity.UserEntity
     *
     * @author : xbx
     * @date : 2022/3/27 10:20
     */
    UserEntity giteeLogin(String token);

    /**
     * 功能描述:
     * <p>获取用户的个数</p>
     *
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/5/4 17:09
     */
    Integer queryAccountCount();

    /**
     * 功能描述:
     * <p>获取最近7天的注册用户个数</p>
     *
     * @return : java.util.Map<java.lang.String,java.lang.Object>
     * @author : xbx
     * @date : 2022/5/14 18:47
     */
    List queryAccountCountByThisWeek();

    /**
     * 功能描述:
     * <p>根据用户id集合产询具体信息</p>
     * @param blogIdAndUserId
     * @return : java.util.List<com.xingbingxuan.blog.account.entity.vo.UserVo>
     * @author : xbx
     * @date : 2022/6/8 22:49
     */
    Map<String,Object> queryUserHeaderByIds(Map<Integer,Integer> blogIdAndUserId);

    /**
     * 功能描述:
     * <p>根据用户查询用户民和密码，用户oauth的登录</p>
     *
     * @param userName
     * @return : com.xingbingxuan.blog.vo.UserVo
     * @author : xbx
     * @date : 2022/6/25 10:33
     */
    UserVo queryUserPasswordByUsername(String userName);

    /**
     * 功能描述:
     * <p>根据token获取用户的信息</p>
     *
     * @param token
     * @return : com.xingbingxuan.blog.vo.UserVo
     * @author : xbx
     * @date : 2022/7/11 22:47
     */
    UserVo queryUserInfoByToken(String token);

}
