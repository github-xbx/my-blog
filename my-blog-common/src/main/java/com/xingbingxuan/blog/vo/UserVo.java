package com.xingbingxuan.blog.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author : xbx
 * @date : 2022/6/12 10:16
 */
@Data
public class UserVo {

    private Long id;
    private String username;

    private String password;

    //头像
    private String header;

    private Date lastLoginTime;

    //角色
    private List<RoleVo> roleVos;
}
