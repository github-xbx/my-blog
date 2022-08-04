package com.xingbingxuan.blog.thirdparty.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xingbingxuan.blog.thirdparty.feign.AccountServiceFeign;
import com.xingbingxuan.blog.thirdparty.service.ThirdAuth;
import com.xingbingxuan.blog.utils.*;
import com.xingbingxuan.blog.vo.UserVo;
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

import java.util.Base64;
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
    public Map<String, Object> giteeLogin(AuthCallback callback)  {

        HashMap<String, Object> result = new HashMap<>();

        AuthRequest giteeAuthRequest = getGiteeAuthRequest();

        AuthResponse login = giteeAuthRequest.login(callback);

        if (login.ok()) {
            JSONObject giteeResultJson = JSONUtil.parseObj(login.getData());

            //log.info("返回的信息 -> {}", giteeResultJson);
            Map<Object, Object> param = new HashMap<>();

            param.put("header", giteeResultJson.get("avatar"));
            param.put("username", giteeResultJson.get("username"));
            param.put("socialUid", giteeResultJson.get("uuid"));
            param.put("socialType", giteeResultJson.get("source"));

            UserVo user = accountServiceFeign.loginAndRegister(param);

            if (user != null) {

                //获取key 用于redis存储
                String key = TokenUtil.getTokenKey(user.getId());

                byte[] serializeKey = SerializeUtil.serializeKey(key);

                byte[] token = SerializeUtil.serializeObject(user.getId() + "-" + DateTool.getNowTimeString("yyyyMMddHHmmss"));
                //将token转换为string 用于返回页面
                String tokenStr = Base64.getEncoder().encodeToString(token);

                //判断用户是否存在
                byte[] bytes = (byte[]) RedisUtil.get(serializeKey);
                if (bytes != null && bytes.length > 0) {
                    //用户的凭证存在
                    RedisUtil.del(serializeKey);
                }
                //存入redis
                RedisUtil.set(serializeKey, token, DateTool.getDayTime(7));

                result.put("login", "success");
                JSONObject userInfo = new JSONObject();
                userInfo.putOnce("token",tokenStr);

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
