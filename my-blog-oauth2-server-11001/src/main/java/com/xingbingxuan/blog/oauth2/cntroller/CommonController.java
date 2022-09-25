package com.xingbingxuan.blog.oauth2.cntroller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : xbx
 * @date : 2022/6/26 10:05
 */
@Controller
public class CommonController {


    @GetMapping("loginHtml")
    public String loginHtml(){

        return "index";
    }



}
