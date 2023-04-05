package com.xingbingxuan.blog.account.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author : xbx
 * @date : 2022/5/3 22:14
 */
@Data
@ToString
public class AdminEntity {

    private Integer adminId;

    private String adminName;

    private String adminPassword;
}
