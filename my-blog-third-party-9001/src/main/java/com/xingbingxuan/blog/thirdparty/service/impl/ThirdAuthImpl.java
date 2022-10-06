package com.xingbingxuan.blog.thirdparty.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xingbingxuan.blog.config.PublicConfigUtil;
import com.xingbingxuan.blog.dto.UserAllInfoDto;
import com.xingbingxuan.blog.param.UserParam;
import com.xingbingxuan.blog.thirdparty.feign.AccountServiceFeign;
import com.xingbingxuan.blog.thirdparty.feign.ThirdAuthorizeServiceFeign;
import com.xingbingxuan.blog.thirdparty.service.ThirdAuth;
import com.xingbingxuan.blog.token.AccessToken;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : xbx
 * @date : 2022/7/23 9:27
 */
@Slf4j
@Service
public class ThirdAuthImpl implements ThirdAuth {


    @Autowired
    private AccountServiceFeign accountServiceFeign;
    @Autowired
    private ThirdAuthorizeServiceFeign thirdAuthorizeServiceFeign;


    /**
     * 功能描述:
     * <p>获取Gitee的authRequest</p>
     *
     * @return : me.zhyd.oauth.request.AuthRequest
     * @author : xbx
     * @date : 2022/7/23 9:33
     */
    private static AuthRequest getGiteeAuthRequest() {
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId(PublicConfigUtil.GITEE_CLIENTID)
                .clientSecret(PublicConfigUtil.GITEE_CLIENTSECRET)
                .redirectUri(PublicConfigUtil.GITEE_REDIRECTURI).build());
    }

    @Override
    public String getGiteeAuthUrl() {

        AuthRequest giteeAuthRequest = getGiteeAuthRequest();

        String authorize = giteeAuthRequest.authorize(AuthStateUtils.createState());


        return authorize;
    }


    @SneakyThrows
    @Override
    @GlobalTransactional(name = "thirdGiteeLogin",rollbackFor = Exception.class)
    public Map<String, Object> giteeLogin(AuthCallback callback)  {

        HashMap<String, Object> result = new HashMap<>();

        //请求gitee服务器获取授权
        AuthRequest giteeAuthRequest = getGiteeAuthRequest();
        AuthResponse login = giteeAuthRequest.login(callback);

        if (login.ok()) {
            //解析返回的数据
            JSONObject giteeResultJson = JSONUtil.parseObj(login.getData());

            //根据第三方的类型和uid在用户表中查询是否有该用户，没有就添加一个
            UserParam userParam = new UserParam();
            userParam.setSocialUid(String.valueOf(giteeResultJson.get("uuid")));
            userParam.setSocialType(String.valueOf(giteeResultJson.get("source")));
            UserAllInfoDto user = accountServiceFeign.thirdPartyLogin(userParam);


            if (user != null) {
                //获取token
                AccessToken accessToken = thirdAuthorizeServiceFeign.loginToken(user);

                result.put("login", "success");
                JSONObject userInfo = new JSONObject();
                userInfo.putOnce("token",accessToken.getToken());

                userInfo.putOnce("user",user);
                result.put("userInfo", userInfo);
            } else {
                result.put("login", "error");

            }

        } else {
            result.put("login", "error");
        }

        return result;
    }
}
