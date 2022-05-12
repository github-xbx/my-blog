package com.xingbingxuan.blog.account.mapper;

import com.xingbingxuan.blog.account.entity.AdminEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : xbx
 * @date : 2022/5/3 22:20
 */
@Mapper
public interface AdminMapper {
    /**
     * 功能描述:
     * <p>添加一个管理员用户</p>
     *
     * @param adminEntity
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/5/3 22:44
     */
    public Integer insertAdmin(AdminEntity adminEntity);
    /**
     * 功能描述:
     * <p>根据管理员名字查询</p>
     *
     * @param adminName
     * @return : com.xingbingxuan.blog.account.entity.AdminEntity
     * @author : xbx
     * @date : 2022/5/3 23:01
     */
    public AdminEntity selectAdminByName(String adminName);
}
