package com.xingbingxuan.blog.http;

/**
 * http 状态码枚举
 * @author : xbx
 * @date : 2022/8/31 22:42
 */
public enum HttpCodeEnum {

    SUCCESS(200),
    NOT_FOUND(404),
    FAIL(500);

    private Integer code;
    private HttpCodeEnum(){}

    HttpCodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
