package com.xingbingxuan.blog.account.service;

import com.xingbingxuan.blog.account.entity.UserEntity;
import org.springframework.stereotype.Service;

/**
 * @author : xbx
 * @date : 2022/3/25 20:09
 */
public interface AccountService {

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

}
