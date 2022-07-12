package com.xingbingxuan.blog.auth.cntroller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xingbingxuan.blog.auth.feign.AccountFeignService;
import com.xingbingxuan.blog.utils.RedisUtil;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
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

    private final String clientId = "25c3fe536bb82c3cab368d52792be50467c59a166835225f923dc7a9be0a2f66";
    private final String clientSecret = "f99a91cd22c62519fe0e640ec8405424a99477565e8a63224d02e8ea6bb4a536";
    private final String redirectUri = "http://localhost:11001/oauth2/gitee/success";
    private final String giteeUrl = "https://gitee.com/oauth/token";


    @Autowired
    private AccountFeignService accountFeignService;
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/oauth2/gitee/success")
    public String oauth2GiteeSuccess(@RequestParam("code") String code) {

        //将code换区accessToken
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("code", code);
        paramMap.put("client_id", clientId);
        paramMap.put("redirect_uri", redirectUri);
        paramMap.put("client_secret", clientSecret);


        HttpResponse giteeResponse = HttpRequest.post(giteeUrl).form(paramMap).execute();
        //System.out.println(execute);
        //登录成功
        if (giteeResponse.getStatus() == 200) {
            JSONObject giteeResultJson = JSONUtil.parseObj(giteeResponse.body());

            Result result = accountFeignService.giteeLogin(giteeResultJson.get("access_token").toString());

            if (result.getCode() == 200) {
                JSONObject user = JSONUtil.parseObj(result.getObject());
                /*//获取jwt token
                String jwt = JwtUtil.creatJWT(new HashMap<String, Object>() {{
                    put("name", user.get("username").toString());
                }});*/
                System.out.println(user);
                //获取token
                String token = "user:token:"+TokenUtil.generateToken("1");
                //存入redis
                RedisUtil.set(token,user.toString(),60*60*24*7);
                //redisTemplate.opsForValue().set(user.get("username"),token , 1, TimeUnit.HOURS);
                return "index";
            } else {
                return "Login";
            }

        } else {
            return "Login";
        }


    }

    @RequestMapping("/oauth/login/redirect")
    @ResponseBody
    public Map oauthRedirect(@RequestParam("code") String code){

        HashMap<String, String> param = new HashMap<>();
        param.put("grant_type","authorization_code");
        String post = HttpUtil.post("http://localhost:11001/oauth/token", String.valueOf(param));

        return null;
    }

}
