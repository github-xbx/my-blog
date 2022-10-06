package com.xingbingxuan.blog.token;

import com.xingbingxuan.blog.utils.RedisUtil;
import com.xingbingxuan.blog.utils.SerializeUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * access_token 工具类
 * @author : xbx
 * @date : 2022/9/24 10:51
 */
public class AccessTokenUtil {

    private static final String PREFIX = "user:";
    private static final String ACCESS = "access:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String AUTH = "auth:";
    private static final int accessTokenValiditySeconds = 60 * 60 * 12; // default 12 hours.


    /**
     * 功能描述:
     * <p>key md5 加密生成秘钥</p>
     *
     * @param values
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/9/24 16:34
     */
    protected static String generateKey(Map<String, String> values) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException nsae) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", nsae);
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", uee);
        }
    }

    /**
     * 功能描述:
     * <p>获取 key加密之后的字符串</p>
     *
     * @param s
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/9/24 16:49
     */
    protected static String  extractKey(String... s){
        Map<String, String> keyMap = new HashMap<>(5);
        for (int i = 0; i < s.length; i++) {
            keyMap.put("param"+i,s[i]);
        }
        String key = generateKey(keyMap);
        return key;
    }

    public static void main(String[] args) throws Exception {
        AccessToken accessToken = new AccessToken();
        accessToken.setToken("121313141231");
        accessToken.setTokenType("banrenr");
        accessToken.setScope("blogUser");

        Authentication authentication = new Authentication();
        authentication.setAuthorities(Arrays.asList(new HashMap(5){{put("authority","ROLE");}}));
        authentication.setScope("blogUser");
        authentication.setUserId("123");
        authentication.setUsername("admin");

//        storeAccessToken(accessToken,authentication);
//        System.out.println("添加成功！！！");
//
//        AccessToken accessToken1 = getAccessToken("121313141231");
//        System.out.println(accessToken1);
//        AccessToken admin = getAccessToken("123", "admin");
//        System.out.println(admin);
//        Authentication authentication1 = getAuthentication("121313141231");
//        System.out.println(authentication1);

        removeAccessToken(accessToken,authentication);
    }

    /**
     * 功能描述:
     * <p>根据用户id和用户名获取access_token </p>
     *
     * @param userId
     * @param username
     * @return : com.xingbingxuan.blog.token.AccessToken
     * @author : xbx
     * @date : 2022/9/24 16:50
     */
    public static  AccessToken getAccessToken(String userId,String username){

        String key = extractKey(userId,username);

        byte[] serializeKey = SerializeUtil.serializeKey(PREFIX + AUTH_TO_ACCESS + key);

        byte[] accessTokenBytes = (byte[]) RedisUtil.get(serializeKey);

        if (accessTokenBytes == null) {
            return null;
        }

        AccessToken accessToken = (AccessToken)SerializeUtil.deserializeObject(accessTokenBytes);

        return accessToken;
    }
    /**
     * 功能描述:
     * <p>根据token获取access_token</p>
     *
     * @param token
     * @return : com.xingbingxuan.blog.token.AccessToken
     * @author : xbx
     * @date : 2022/9/24 21:55
     */
    public static AccessToken getAccessToken(String token){

        byte[] key = SerializeUtil.serializeKey(PREFIX + ACCESS + token);

        byte[] accessTokenBytes = (byte[])  RedisUtil.get(key);

        if (accessTokenBytes == null) {
            return null;
        }

        AccessToken accessToken = (AccessToken)SerializeUtil.deserializeObject(accessTokenBytes);

        return accessToken;
    }
    /**
     * 功能描述:
     * <p>根据token获取authentication，用户权限信息</p>
     *
     * @param token
     * @return : com.xingbingxuan.blog.token.Authentication
     * @author : xbx
     * @date : 2022/9/24 21:58
     */
    public static Authentication getAuthentication(String token){

        byte[] authKey = SerializeUtil.serializeKey(PREFIX + AUTH + token);

        byte[] authenticationBytes = (byte[])  RedisUtil.get(authKey);

        if (authenticationBytes == null){
            return  null;
        }
        Authentication authentication = (Authentication) SerializeUtil.deserializeObject(authenticationBytes);

        return authentication;
    }
    /**
     * 功能描述:
     * <p>保存一个用户的所有的token信息</p>
     *
     * @param accessToken
     * @param authentication
     * @return : void
     * @author : xbx
     * @date : 2022/9/24 21:47
     */
    public static void storeAccessToken(AccessToken accessToken,Authentication authentication) throws Exception {

        byte[] serializeToken = SerializeUtil.serializeObject(accessToken);
        byte[] serializeAuthentication = SerializeUtil.serializeObject(authentication);

        byte[] tokenKey = SerializeUtil.serializeKey(PREFIX + ACCESS + accessToken.getToken());
        byte[] autoToTokenKey = SerializeUtil.serializeKey(PREFIX + AUTH_TO_ACCESS + extractKey(authentication.getUserId(), authentication.getUsername()));
        byte[] authKey = SerializeUtil.serializeKey(PREFIX + AUTH + accessToken.getToken());

        RedisUtil.set(tokenKey, serializeToken, accessTokenValiditySeconds);
        RedisUtil.set(autoToTokenKey,serializeToken,accessTokenValiditySeconds);
        RedisUtil.set(authKey,serializeAuthentication,accessTokenValiditySeconds);


    }
    /**
     * 功能描述:
     * <p>删除一个用户的所有的token信息</p>
     *
     * @param accessToken
     * @param authentication
     * @return : void
     * @author : xbx
     * @date : 2022/9/24 22:15
     */
    public static void removeAccessToken(AccessToken accessToken,Authentication authentication) throws Exception {
        byte[] tokenKey = SerializeUtil.serializeKey(PREFIX + ACCESS + accessToken.getToken());
        byte[] autoToTokenKey = SerializeUtil.serializeKey(PREFIX + AUTH_TO_ACCESS + extractKey(authentication.getUserId(), authentication.getUsername()));
        byte[] authKey = SerializeUtil.serializeKey(PREFIX + AUTH + accessToken.getToken());
        try {
            RedisUtil.del(tokenKey);
            RedisUtil.del(autoToTokenKey);
            RedisUtil.del(authKey);
        }catch (Exception e){
            throw new Exception("redis token删除操作出现错误");
        }

    }


    /**
     * 功能描述:
     * <p>生成32位随机字符串</p>
     *
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/9/25 10:31
     */
    public static String getToken(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        return token;
    }
}
