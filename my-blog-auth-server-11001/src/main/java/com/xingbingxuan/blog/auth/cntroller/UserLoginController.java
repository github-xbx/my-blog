package com.xingbingxuan.blog.auth.cntroller;

import com.xingbingxuan.blog.auth.feign.AccountFeignService;
import com.xingbingxuan.blog.auth.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.xingbingxuan.blog.utils.Result;

/**
 * @author : xbx
 * @date : 2022/3/24 22:00
 */
@Controller
public class UserLoginController {


    @Autowired
    private AccountFeignService accountFeignService;





    @GetMapping("success")
    public String success(){

        return "consent";
    }

    @GetMapping("failure")
    public String failure(){

        return "login";
    }

}
