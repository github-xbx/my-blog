package com.xingbingxuan.blog.config;

import com.xingbingxuan.blog.utils.CommonTool;

import java.io.IOException;
import java.util.Properties;

/**
 * @author : xbx
 * @date : 2022/6/30 21:40
 */
public class PublicConfigUtil {

    public static String CLIENTID;

    public static String CLIENTSECRET;

    public static String REDIRECTURI;

    public static String OAUTHTOKENURI;

    public static String OAUTHCHECKTOKENURI;

    public static String GITEE_CLIENTID;

    public static String GITEE_CLIENTSECRET;

    public static String GITEE_REDIRECTURI;

    public static String GITEE_OAUTHTOKENURI;

    /*
      blog主页的域名
     */
    public static String BLOG_HOME_DOMAIN;
    /**
     * 用户默认密码
     */
    public static String USER_DEFAULT_PASSWORD;
    /**
     * 用户默认头衔url
     */
    public static String USER_DEFAULT_HEADER_IMAGE_URL;

    private static Properties properties;

    static {
        try {
            properties = CommonTool.getPropertiesContent("PublicConfigInfo.properties");
            CLIENTID = properties.getProperty("oauth.blog.clientId");
            CLIENTSECRET = properties.getProperty("oauth.blog.clientSecret");
            REDIRECTURI = properties.getProperty("oauth.blog.redirectUri");
            OAUTHTOKENURI = properties.getProperty("oauth.blog.getTokenUrl");
            OAUTHCHECKTOKENURI = properties.getProperty("oauth.blog.checkTokenUrl");
            GITEE_CLIENTID = properties.getProperty("gitee.clientId");
            GITEE_CLIENTSECRET = properties.getProperty("gitee.clientSecret");
            GITEE_REDIRECTURI = properties.getProperty("gitee.redirectUri");
            GITEE_OAUTHTOKENURI = properties.getProperty("gitee.giteeOauthTokenUrl");
            BLOG_HOME_DOMAIN = properties.getProperty("blog.home.domain");
            USER_DEFAULT_PASSWORD = properties.getProperty("user.default.password");
            USER_DEFAULT_HEADER_IMAGE_URL = properties.getProperty("user.default.headerImageUrl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
