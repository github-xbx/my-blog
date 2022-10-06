package com.xingbingxuan.blog.dto;

import com.xingbingxuan.blog.vo.RoleVo;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 服务数据传输对象 用户的所有信息
 * @author : xbx
 * @date : 2022/9/3 17:35
 */
@Data
@ToString
public class UserAllInfoDto {

    private Long id;
    private String username;

    private String password;

    //头像
    private String header;

    private Date lastLoginTime;

    private List<RoleVo> roleVos;
}
