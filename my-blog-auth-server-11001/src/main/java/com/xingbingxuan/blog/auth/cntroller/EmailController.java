package com.xingbingxuan.blog.auth.cntroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : xbx
 * @date : 2022/3/25 19:46
 */
@Controller
public class EmailController {

    @GetMapping("/auth2/email")
    public String emailAuthPage(){
        return "redirect:/email.html";
    }
}
