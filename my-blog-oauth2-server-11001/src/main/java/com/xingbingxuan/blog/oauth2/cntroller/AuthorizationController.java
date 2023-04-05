package com.xingbingxuan.blog.oauth2.cntroller;

import com.xingbingxuan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Map;

/**
 * 授权 controller
 * @author : xbx
 * @date : 2023/4/2 18:39
 */
@RestController
public class AuthorizationController {


    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
     * Oauth2登录认证
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result<OAuth2AccessToken> postAccessToken(@RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException, HttpRequestMethodNotSupportedException {

        parameters.put("client_id","BLOGUSER");
        parameters.put("client_secret","00000000");
        parameters.put("grant_type","password");

        User user = new User(parameters.get("client_id"), parameters.get("client_secret"), new HashSet<>());
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null,new HashSet<>());


        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(passwordAuthenticationToken, parameters).getBody();


        return Result.success(oAuth2AccessToken);
    }


}
