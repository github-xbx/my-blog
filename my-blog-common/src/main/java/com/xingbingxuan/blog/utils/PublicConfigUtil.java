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

    private static Properties properties;

    static {
        try {
            properties = CommonTool.getPropertiesContent("publicConfig.properties");
            CLIENTID = properties.getProperty("oauth.blog.clientId");
            CLIENTSECRET = properties.getProperty("oauth.blog.clientSecret");
            REDIRECTURI = properties.getProperty("oauth.blog.redirectUri");
            OAUTHTOKENURI = properties.getProperty("oauth.blog.getTokenUrl");
            OAUTHCHECKTOKENURI = properties.getProperty("oauth.blog.checkTokenUrl");
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
