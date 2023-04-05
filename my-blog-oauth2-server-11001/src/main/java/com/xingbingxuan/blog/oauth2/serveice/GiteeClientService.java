package com.xingbingxuan.blog.oauth2.serveice;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xingbingxuan.blog.config.PublicConfigUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

/**
 * “/login/authorization_code”接口对应了我们在Github中配置的回调函数，
 * 即在Github登录验证成功后，就会回调该接口，我们就是就在回调方法中，
 * 模拟了用户名密码登录的方式，调用了SpringSecurity登录认证需要的“/login/doLogin”接口。
 * 这里，我们通过queryAccessToken()方法根据回调传递的code获取对应的accessToken，
 * 然后把accessToken作为登录使用的principal 参数值，之而立不需要传递密码，因为我们经过Github授权，
 * 就可以认为完成了登录认证的判断过程了。
 * @author xbx
 */
@Service
@Slf4j
public class GiteeClientService {

    private static String clientId = PublicConfigUtil.GITEE_CLIENTID;
    private static String clientSecret = PublicConfigUtil.GITEE_CLIENTSECRET;
    private static String redirectUri = PublicConfigUtil.GITEE_REDIRECTURI;

    @Autowired
    private RestTemplate restTemplate;

    public static AuthRequest getGiteeAuthRequest() {
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri).build());
    }

    /**
     * 功能描述:
     * <p>获取gitee的用户信息</p>
     *
     * @param code
     * @param state
     * @return : java.util.Map<java.lang.String,java.lang.String>
     * @author : xbx
     * @date : 2022/9/5 21:47
     */
    public Map<String, String> queryAccessToken(String code,String state){

        HashMap<String, String> result = new HashMap<>();

        AuthRequest giteeAuthRequest = getGiteeAuthRequest();

        AuthCallback callback = new AuthCallback();
        callback.setCode(code);
        callback.setState(state);
        AuthResponse login = giteeAuthRequest.login(callback);

        if (login.ok()) {
            JSONObject giteeResultJson = JSONUtil.parseObj(login.getData());

            log.info("gitee返回的用户信息 -> {}",giteeResultJson);

            result.put("header", String.valueOf(giteeResultJson.get("avatar")));
            result.put("username", String.valueOf(giteeResultJson.get("username")));
            result.put("socialUid", String.valueOf(giteeResultJson.get("uuid")));
            result.put("socialType", String.valueOf(giteeResultJson.get("source")));

        } else {
            return null;
        }

        return result;
    }


}