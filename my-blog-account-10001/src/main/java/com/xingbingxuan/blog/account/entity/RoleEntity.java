package com.xingbingxuan.blog.account.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author : xbx
 * @date : 2022/8/31 23:18
 */
@Data
@ToString
public class RoleEntity {

    private Integer roleId;

    private String roleName;

    private String roleCode;

    private String roleDescription;

}
