package com.xingbingxuan.blog.account.mapper;

import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.entity.bo.UserAndRoleRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/3/26 21:48
 */
@Mapper
public interface UserMapper {
    /**
     * 功能描述:
     * <p>根据条件查询一个</p>
     *
     * @param userEntity 账户是实体类 根据对象里面数据查询
     * @return : com.xingbingxuan.blog.account.entity.UserEntity
     *
     * @author : xbx
     * @date : 2022/3/26 21:50
     */
    UserAndRoleRelation selectOneAnd(UserEntity userEntity);

    /**
     * 功能描述:
     * <p>查询所有的用户</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.account.entity.UserEntity>
     * @author : xbx
     * @date : 2022/5/15 11:53
     */
    List<UserEntity> selectAllUser();

    Integer insertAccount(UserEntity userEntity);

    /**
     * 功能描述:
     * <p>插入一条数据，如果该数据存在怎更新它的登录时间，如果该数据不存在插入一条新数据</p>
     *
     * @param user
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/7/25 21:50
     */
    Integer insertByUsernameUpdate(UserEntity user);

    Integer selectAccountCount();

    /**
     * 功能描述:
     * <p>查询最近7天注册的用户</p>
     *
     * @return : java.util.List<com.xingbingxuan.blog.account.entity.UserEntity>
     * @author : xbx
     * @date : 2022/5/14 18:25
     */
    List<UserEntity> selectByThisWeek();

    /**
     * 功能描述:
     * <p>根据用户ids查询所有信息</p>
     * @param ids
     * @return : java.util.List<com.xingbingxuan.blog.account.entity.vo.UserVo>
     * @author : xbx
     * @date : 2022/6/8 22:50
     */
    List<UserEntity> selectAllUserByIds(List ids);


    /**
     * 功能描述:
     * <p>根据用户名查询密码</p>
     *
     * @param username
     * @return : com.xingbingxuan.blog.account.entity.UserEntity
     * @author : xbx
     * @date : 2022/6/26 9:39
     */
    UserAndRoleRelation selectPasswordByUserName(String username);

    /**
     * 功能描述:
     * <p>根据id更改用户的信息</p>
     *
     * @return : int
     * @author : xbx
     * @date : 2022/7/17 18:16
     */
    int updateUserById(UserEntity userEntity);

    /**
     * 功能描述:
     * <p>根据用户名或邮箱查询用户信息包括角色信息（用于登录验证）</p>
     *
     * @param userEntity
     * @return : com.xingbingxuan.blog.account.entity.bo.UserAndRoleBo
     * @author : xbx
     * @date : 2022/10/1 10:19
     */
    UserAndRoleRelation selectPasswordByUserNameOrEmail(UserEntity userEntity);
}
