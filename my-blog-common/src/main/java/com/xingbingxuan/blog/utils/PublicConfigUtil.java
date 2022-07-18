package com.xingbingxuan.blog.utils;

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


    private static Properties properties;

    static {
        try {
            properties = CommonTool.getPropertiesContent("publicConfig.properties");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static String getClientId(){
//        return CLIENTID;
//    }
//
//    public static String getClientSecret(){
//        return CLIENTSECRET;
//    }
//
//    public static String getRedirectUri(){
//        return REDIRECTURI;
//    }
}
