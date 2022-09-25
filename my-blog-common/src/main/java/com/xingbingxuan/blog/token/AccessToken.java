package com.xingbingxuan.blog.token;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author : xbx
 * @date : 2022/9/24 10:09
 */
@Data
@ToString
public class AccessToken implements Serializable {

    private static final long serialVersionUID = -4165578808721546897L;
    private String token;
    private String tokenType;
//    private Integer expiresIn;
    private String scope;


}
