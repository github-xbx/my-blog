package com.xingbingxuan.blog.account.mapper;

import com.xingbingxuan.blog.account.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : xbx
 * @date : 2022/8/31 23:20
 */
@Mapper
public interface RoleMapper {
    /**
     * 功能描述:
     * <p>根据role的id集合查询role信息</p>
     *
     * @param roleIds
     * @return : java.util.List<com.xingbingxuan.blog.account.entity.RoleEntity>
     * @author : xbx
     * @date : 2022/8/31 23:22
     */
    List<RoleEntity> selectAllByIds(List<Integer> roleIds);

    /**
     * 功能描述:
     * <p>多表 `roel` `role_user_relation` 两个表联查 根据用户的id 查询其所有的角色 </p>
     *
     * @param userId 参数用户id
     * @return : com.xingbingxuan.blog.account.entity.RoleEntity
     * @author : xbx
     * @date : 2022/9/3 17:38
     */
    List<RoleEntity> selectAllRoleByUserId(@Param("userId")Long userId);

    /**
     * 功能描述:
     * <p>根据id查询role 信息</p>
     *
     * @param id
     * @return : com.xingbingxuan.blog.account.entity.RoleEntity
     * @author : xbx
     * @date : 2022/9/5 23:36
     */
    RoleEntity selectOneById(Integer id);
}
