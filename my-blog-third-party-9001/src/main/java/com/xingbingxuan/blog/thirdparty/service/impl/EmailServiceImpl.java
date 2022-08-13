package com.xingbingxuan.blog.thirdparty.service.impl;

import com.xingbingxuan.blog.thirdparty.service.EmailService;
import com.xingbingxuan.blog.utils.CodeUtil;
import com.xingbingxuan.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

/**
 * @author : xbx
 * @date : 2022/8/12 22:53
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final String REDIS_CODE_PREFIX = "blog:VerificationCode:";

    @Override
    public String sendCodeEmail(String toMail) throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

        helper.setFrom(new InternetAddress(MimeUtility.encodeText("MY-BLOG")+"<xingbingxuanblog@163.com>").toString());
        helper.setTo(toMail);
        helper.setSubject("MY-BLOG 验证码");
        String sixVerificationCode = CodeUtil.createSixVerificationCode();
        helper.setText(buildContent(sixVerificationCode),true);
        //存入redis中有效时间为10分钟
        RedisUtil.set(REDIS_CODE_PREFIX+toMail,sixVerificationCode,60*10);
        mailSender.send(mimeMessage);


        return "邮件发送成功";

    }
    public String buildContent(String code) throws Exception{


        //加载邮件html模板
        Resource resource = new ClassPathResource("templates/email/codeMail.ftl");
        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            inputStream = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("发送邮件失败");
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IOException(e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IOException(e);
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), code);


    }


}
