package com.xingbingxuan.blog.account.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xingbingxuan.blog.account.entity.UserEntity;
import com.xingbingxuan.blog.account.service.AccountService;
import com.xingbingxuan.blog.account.entity.vo.UserLoginVo;
import com.xingbingxuan.blog.utils.*;
import com.xingbingxuan.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : xbx
 * @date : 2022/3/24 21:29
 */
@Slf4j
@Controller
@RequestMapping("/user/account")
public class LoginController {


    @Autowired
    private AccountService accountService;




    @PostMapping("/giteeLogin")
    @ResponseBody
    public Result giteeLogin(@RequestBody String accessToken){

        UserEntity userEntity = accountService.giteeLogin(accessToken);

        if (userEntity == null){

            return Result.error("登录失败");
        }else {
            return Result.success("登录成功",userEntity);
        }
    }

    /**
     * 功能描述:
     * <p>第三方登录，gitee登录</p>
     *
     * @param code
     * @return : org.springframework.web.servlet.ModelAndView
     * @author : xbx
     * @date : 2022/7/16 17:14
     */
    @GetMapping("/oauth2/gitee/success")
    public ModelAndView oauth2GiteeSuccess(@RequestParam("code") String code) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        //将code换区accessToken
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("code", code);
        paramMap.put("client_id", PublicConfigUtil.GITEE_CLIENTID);
        paramMap.put("redirect_uri", PublicConfigUtil.GITEE_REDIRECTURI);
        paramMap.put("client_secret", PublicConfigUtil.GITEE_CLIENTSECRET);


        HttpResponse giteeResponse = HttpRequest.post(PublicConfigUtil.GITEE_OAUTHTOKENURI).form(paramMap).execute();
        //System.out.println(execute);
        //登录成功
        if (giteeResponse.getStatus() == 200) {
            JSONObject giteeResultJson = JSONUtil.parseObj(giteeResponse.body());

            UserEntity user = accountService.giteeLogin(giteeResultJson.get("access_token").toString());


            if (user != null){


                String key = TokenUtil.getTokenKey(user.getId());

                byte[] serializeKey = SerializeUtil.serializeKey(key);

                byte[] token = SerializeUtil.serializeObject(user.getId()+"-"+DateTool.getNowTimeString("yyyyMMddHHmmss"));
                //将token转换为string 用于返回页面
                String tokenStr = Base64.getEncoder().encodeToString(token);

                //判断用户是否存在
                byte[] bytes = (byte[]) RedisUtil.get(serializeKey);
                if (bytes != null && bytes.length>0){
                    //用户的凭证存在
                    RedisUtil.del(serializeKey);
                }
                //存入redis
                RedisUtil.set(serializeKey,token, DateTool.getDayTime(7));


                modelAndView.setViewName("login");
                modelAndView.addObject("token",tokenStr);
                modelAndView.addObject("domain",PublicConfigUtil.BLOG_HOME_DOMAIN);

            }else {
                modelAndView.setViewName("error");

            }

        }else {
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }


}
