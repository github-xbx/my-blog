package com.xingbingxuan.blog.exception;

/**
 * 自定义异常 用户名不存在异常
 * @author : xbx
 * @date : 2022/10/6 18:50
 */
public class UsernameNotFoundException extends RuntimeException {

    /**
     * Constructs a <code>UsernameNotFoundException</code> with the specified message.
     * @param msg the detail message.
     */
    public UsernameNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs a {@code UsernameNotFoundException} with the specified message and root
     * cause.
     * @param msg the detail message.
     * @param cause root cause
     */
    public UsernameNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
