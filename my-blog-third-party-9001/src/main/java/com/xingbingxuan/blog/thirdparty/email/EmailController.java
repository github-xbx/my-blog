package com.xingbingxuan.blog.thirdparty.email;

import com.xingbingxuan.blog.thirdparty.service.EmailService;
import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 邮箱服务接口
 * @author : xbx
 * @date : 2022/8/12 21:48
 */
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/email/emailGetCode/{userEmail}")
    public Result<String> emailGetCode(@PathVariable String userEmail) throws Exception {

        String s = emailService.sendCodeEmail(userEmail);

        return Result.success(s);

    }
}
