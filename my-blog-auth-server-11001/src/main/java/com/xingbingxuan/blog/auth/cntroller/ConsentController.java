package com.xingbingxuan.blog.auth.cntroller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.*;

/**
 * @author : xbx
 * @date : 2022/6/26 10:05
 */
@Controller
public class ConsentController {


    @ResponseBody
    @GetMapping("/favicon.ico")
    public String faviconico(){
        return "favicon.ico";
    }

    @GetMapping("loginHtml")
    public String loginHtml(){

        return "index";
    }



}
