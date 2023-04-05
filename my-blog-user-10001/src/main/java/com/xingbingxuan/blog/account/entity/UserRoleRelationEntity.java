package com.xingbingxuan.blog.account.entity;

import lombok.Data;

/**
 * @author : xbx
 * @date : 2022/9/5 23:12
 */
@Data
public class UserRoleRelationEntity {

    private Integer id;

    private Long userId;

    private Integer roleId;

}
