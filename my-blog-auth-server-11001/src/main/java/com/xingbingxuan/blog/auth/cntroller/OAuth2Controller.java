package com.xingbingxuan.blog.auth.cntroller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xingbingxuan.blog.auth.feign.AccountFeignService;
import com.xingbingxuan.blog.auth.feign.ThirdPartyFeignService;
import com.xingbingxuan.blog.auth.serveice.GiteeClientService;
import com.xingbingxuan.blog.utils.RedisUtil;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 第三方的社交登录 controller
 *
 * @author : xbx
 * @date : 2022/3/25 16:32
 */
@Controller
@Slf4j
public class OAuth2Controller {


    @Autowired
    private ThirdPartyFeignService thirdPartyFeignService;



    @RequestMapping("giteeUrl")
    public String giteeUrl(){
        AuthRequest giteeAuthRequest = GiteeClientService.getGiteeAuthRequest();
        String authorize = giteeAuthRequest.authorize(AuthStateUtils.createState());


        return "redirect:" + authorize;
    }

}
