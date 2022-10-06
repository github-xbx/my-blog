package com.xingbingxuan.blog.exception;

/**
 * 自定义异常 凭据错误异常
 * @author xbx
 * @version 1.0
 * @date 2022/10/6 18:49
 */
public  class BadCredentialsException extends RuntimeException {


    public BadCredentialsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BadCredentialsException(String msg) {
        super(msg);
    }

}