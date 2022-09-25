package com.xingbingxuan.blog.token;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * token信息
 * @author : xbx
 * @date : 2022/9/24 10:42
 */
@Data
@ToString
public class Authentication implements Serializable {

    private static final long serialVersionUID = 4254281320590385048L;
    private List authorities; //权限(角色)
    private String username;
    private String userId;
    private String scope;
}
