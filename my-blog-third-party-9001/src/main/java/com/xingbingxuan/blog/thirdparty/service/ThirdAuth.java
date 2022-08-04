package com.xingbingxuan.blog.thirdparty.service;

import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;

import java.util.Map;

/**
 * 第三方授权服务接口
 * @author : xbx
 * @date : 2022/7/23 9:25
 */
public interface ThirdAuth {

    /**
     * 功能描述:
     * <p>获取第三方应用gitee的授权url</p>
     *
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/7/23 9:26
     */
    public String getGiteeAuthUrl();


    /**
     * 功能描述:
     * <p>第三方登录回调</p>
     *
     * @param callback
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/7/23 17:12
     */
    public Map<String,Object> giteeLogin(AuthCallback callback);
}
