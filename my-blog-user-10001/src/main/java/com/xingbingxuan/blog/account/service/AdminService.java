package com.xingbingxuan.blog.account.service;

import com.xingbingxuan.blog.account.entity.AdminEntity;

/**
 * @author : xbx
 * @date : 2022/5/3 22:45
 */
public interface AdminService {

    /**
     * 功能描述:
     * <p>添加一个用户</p>
     *
     * @param adminEntity
     * @return : java.lang.Integer
     * @author : xbx
     * @date : 2022/5/4 9:45
     */
    public Integer addAdmin(AdminEntity adminEntity);
    /**
     * 功能描述:
     * <p>登录管理员账户</p>
     *
     * @param adminEntity
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/5/4 9:45
     */
    public String login(AdminEntity adminEntity);
}
