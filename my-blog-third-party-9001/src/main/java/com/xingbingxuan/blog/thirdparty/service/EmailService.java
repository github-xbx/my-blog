package com.xingbingxuan.blog.thirdparty.service;

import javax.mail.MessagingException;

/**
 * @author : xbx
 * @date : 2022/8/12 22:52
 */
public interface EmailService {


    /**
     * 功能描述:
     * <p>发送验证码邮件</p>
     *
     * @param toMail
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/8/12 22:59
     */
    public String sendCodeEmail(String toMail) throws Exception;
}
