package com.xingbingxuan.blog.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.io.IOException;
import java.util.Properties;

/**
 * @author : xbx
 * @date : 2022/4/23 21:46
 */
public class TokenUtil {




    /**
     * 功能描述:
     * <p>获取存在redis中的token的key</p>
     *
     * @param o
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/7/16 23:27
     */
    public static String getTokenKey(Object o){
        return "user:token:"+o.toString();
    }
    /**
     * 功能描述:
     * <p>解析token中的用户信息</p>
     *
     * @param token
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/7/16 23:30
     */
    public static String getObjectByToken(byte[] token){

        String tokenStr = (String) SerializeUtil.deserializeObject(token);

        tokenStr = tokenStr.substring(0,tokenStr.lastIndexOf("-"));
        return tokenStr;
    }
    /**
     * 功能描述:
     * <p>获取token</p>
     *
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/4/21 22:09
     */
    public static String generateToken(String username){
        String token = null;
        String data = username +":"+ DateTool.getNowTimeString("yyyyMMddHHmmss");
        try {
            Properties properties = CommonTool.getPropertiesContent("key.properties");
            token = encryption( username,properties.getProperty("privateKey"),properties.getProperty("publicKey"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return token;
    }

    /**
     * 功能描述:
     * <p>解析token，获取token中的内容</p>
     *
     * @param token
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/5/8 22:52
     */
    public static String getTokenContent(String token){
        String content = null;

        try {
            Properties properties = CommonTool.getPropertiesContent("key.properties");
            content = decrypt(token,properties.getProperty("privateKey"),properties.getProperty("publicKey"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return content;
    }
    /*加密*/
    public  static String encryption(String text,String privateKey,String publicKey){
        RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), privateKey, publicKey);
        // 公钥加密，私钥解密
        String encryptByPublic = rsa.encryptBase64(text, KeyType.PublicKey);

        return encryptByPublic;
    }

    /*解密*/
    public static String decrypt(String text,String privateKey,String publicKey){
        RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), privateKey, publicKey);
        // 公钥加密，私钥解密
        String decryptByPrivate = rsa.decryptStr(text, KeyType.PrivateKey);
        return decryptByPrivate;
    }
    /**
     * 功能描述:
     * <p> 添加用户到缓存，并生成它的token</p>
     *
     * @param user 用户信系
     * @param time 过期时间 单位S秒
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/4/23 22:49
     */
    public static String addUser(String user ,long time){
        String token = generateToken(user);
        if (authToken(token)){
            token = generateToken(user);
        }
        boolean set = RedisUtil.set(token, user, time);
        if (set){
            return token;
        }else {
            return null;
        }
    }

    /**
     * 功能描述:
     * <p> 验证token是否存在</p>
     *
     * @param token
     * @return : java.lang.Boolean
     * @author : xbx
     * @date : 2022/4/23 22:45
     */
    public static Boolean authToken(String token){

        //根据token，在redis中查询是否存在
        boolean b = RedisUtil.hasKey(token);

        return b;

    }

    /**
     * 功能描述:
     * <p> 根据token获取用户信息</p>
     *
     * @param token
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/4/23 22:48
     */
    public static String getUserByToken(String token){
        Object o = RedisUtil.get(token);
        return o.toString();
    }

}
