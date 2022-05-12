package com.xingbingxuan.blog.account.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author : xbx
 * @date : 2022/3/24 22:11
 */
@Data
public class UserEntity {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String mobile;

    private String email;

    //头像
    private String header;

    private Integer sex;

    //个性签名
    private String sign;

    //积分
    private Integer integartion;

    //成长值
    private Integer growth;

    //启用状态
    private Integer status;

    private String socialType;

    private String socialUid;


    private Date createTime;
}