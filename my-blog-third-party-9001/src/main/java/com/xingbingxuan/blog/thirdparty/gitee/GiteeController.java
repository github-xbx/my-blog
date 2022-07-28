package com.xingbingxuan.blog.thirdparty.gitee;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xingbingxuan.blog.thirdparty.service.ThirdAuth;
import com.xingbingxuan.blog.utils.*;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;

/**
 * @author : xbx
 * @date : 2022/7/23 9:10
 */
@Controller
public class GiteeController {

    @Autowired
    private ThirdAuth thirdAuth;

    /**
     * 功能描述:
     * <p>获取gitee授权登录的url</p>
     *
     * @return : com.xingbingxuan.blog.utils.Result<java.lang.String>
     * @author : xbx
     * @date : 2022/7/24 22:17
     */
    @GetMapping("gitee/login")
    @ResponseBody
    public Result<String> getGiteeUrl(){
        String giteeAuthUrl = thirdAuth.getGiteeAuthUrl();

        return Result.success(giteeAuthUrl);
    }


}
