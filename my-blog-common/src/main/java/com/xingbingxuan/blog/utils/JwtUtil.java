package com.xingbingxuan.blog.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author : xbx
 * @date : 2022/3/27 18:25
 */
public class JwtUtil {

    public static String getPropertiesByKey(String path,String key){
        ClassPathResource resource = new ClassPathResource(path);
        Properties properties = new Properties();
        String value;
        try {
            properties.load(resource.getStream());
            value = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return value;
    }

    public static String creatJWT(Map jwtData){
        String propertiesByKey = getPropertiesByKey("jwtConfig.properties", "JWT.Secret-key");

        String token = JWT.create()
                .addPayloads(jwtData)
                .setKey(propertiesByKey.getBytes())
                .sign();
        return token;
    }
}
