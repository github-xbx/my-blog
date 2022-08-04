package com.xingbingxuan.blog.account.entity;

import lombok.Data;

/**
 * 用户关注实体
 * @author : xbx
 * @date : 2022/6/20 23:03
 */
@Data
public class UserFollowEntity {

    private Integer followId;

    private Integer userId;

    private Integer followUserId;

}
