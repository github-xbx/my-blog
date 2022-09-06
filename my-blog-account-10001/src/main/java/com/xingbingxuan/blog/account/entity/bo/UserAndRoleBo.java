package com.xingbingxuan.blog.account.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xingbingxuan.blog.account.entity.RoleEntity;
import com.xingbingxuan.blog.vo.RoleVo;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author : xbx
 * @date : 2022/9/3 17:52
 */
@Data
@ToString
public class UserAndRoleBo {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String mobile;

    private String email;


    /**
     * 头像
     */
    private String header;

    private Integer sex;


    /**
     * 个性签名
     */
    private String sign;


    /**
     * 积分
     */
    private Integer integration;


    /**
     * 成长值
     */
    private Integer growth;


    /**
     * 启用状态
     */
    private Integer status;

    /**
     * 第三方登录类型
     */
    private String socialType;

    /**
     * 第三方登录Uid
     */
    private String socialUid;



    private Date createTime;


    private Date lastLoginTime;

    /**
     * 角色信息
     */
    private List<RoleVo> roleVos;
}
