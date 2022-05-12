package com.xingbingxuan.blog.account.mapper;

import com.xingbingxuan.blog.account.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : xbx
 * @date : 2022/3/26 21:48
 */
@Mapper
public interface AccountMapper {
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
    UserEntity selectOneAnd(UserEntity userEntity);

    Integer insertAccount(UserEntity userEntity);

    Integer selectAccountCount();
}
