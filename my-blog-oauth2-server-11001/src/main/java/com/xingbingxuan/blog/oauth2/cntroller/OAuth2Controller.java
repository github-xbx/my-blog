package com.xingbingxuan.blog.oauth2.cntroller;

import com.xingbingxuan.blog.oauth2.serveice.GiteeClientService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 第三方的社交登录 controller
 *
 * @author : xbx
 * @date : 2022/3/25 16:32
 */
@Controller
@Slf4j
public class OAuth2Controller {




    @RequestMapping("giteeUrl")
    public String giteeUrl(){
        AuthRequest giteeAuthRequest = GiteeClientService.getGiteeAuthRequest();
        String authorize = giteeAuthRequest.authorize(AuthStateUtils.createState());

        return "redirect:" + authorize;
    }

}
