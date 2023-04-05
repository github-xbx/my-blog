package com.xingbingxuan.blog.account.mapper;

import com.xingbingxuan.blog.account.entity.UserRoleRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : xbx
 * @date : 2022/9/5 23:13
 */
@Mapper
public interface UserRoleRelationMapper {

    /**
     * 功能描述:
     * <p>添加一个用户的角色信息</p>
     *
     * @param userRoleRelationEntity
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/9/5 23:14
     */
    Integer insertUserRoleRelation(UserRoleRelationEntity userRoleRelationEntity);

}
