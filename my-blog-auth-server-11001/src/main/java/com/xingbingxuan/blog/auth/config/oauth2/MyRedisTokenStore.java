package com.xingbingxuan.blog.auth.config.oauth2;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 重写RedisTokenStore
 * 实现自定义的token redis存储
 * @author : xbx
 * @date : 2022/6/30 22:54
 */
public class MyRedisTokenStore implements TokenStore {

    //reids key 前缀常量
    private static final String ACCESS = "access:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String AUTH = "auth:";
    private static final String REFRESH_AUTH = "refresh_auth:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static final String REFRESH = "refresh:";
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";
    private static final String CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    private static final String UNAME_TO_ACCESS = "uname_to_access:";

    //判断当前class loader中是否存在对应的类型了；
    private static final boolean springDataRedis_2_0 = ClassUtils.isPresent(
            "org.springframework.data.redis.connection.RedisStandaloneConfiguration",
            MyRedisTokenStore.class.getClassLoader());
    //redis 连接工厂
    private final RedisConnectionFactory connectionFactory;
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    private String prefix = "";

    private Method redisConnectionSet_2_0;

    //构造方法 如果加载的了redis 实例化redis的set方法
    public MyRedisTokenStore(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        if (springDataRedis_2_0) {
            this.loadRedisConnectionMethods_2_0();
        }
    }

    public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
        this.authenticationKeyGenerator = authenticationKeyGenerator;
    }

    public void setSerializationStrategy(RedisTokenStoreSerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    private void loadRedisConnectionMethods_2_0() {
        //ReflectionUtils spring 反射工具类，
        //通过反射的方式 实例化RedisConnection 中的set方法
        this.redisConnectionSet_2_0 = ReflectionUtils.findMethod(
                RedisConnection.class, "set", byte[].class, byte[].class);
    }

    private RedisConnection getConnection() {
        return connectionFactory.getConnection();
    }

    //序列化
    private byte[] serialize(Object object) {
        return serializationStrategy.serialize(object);
    }

    /**
     * 序列化key，供redis使用，redis的key
     * @Author xbx
     * @Date 9:56 2022/7/1
     **/
    private byte[] serializeKey(String object) {
        return serialize(prefix + object);
    }

    //反序列化accessToken
    private OAuth2AccessToken deserializeAccessToken(byte[] bytes) {
        return serializationStrategy.deserialize(bytes, OAuth2AccessToken.class);
    }


    private Map deserializeMap(byte[] bytes){
        return serializationStrategy.deserialize(bytes,Map.class);
    }

    /**
     * 反序列化 身份验证
     * @Author xbx
     * @Date 9:45 2022/7/1
     **/
    private OAuth2Authentication deserializeAuthentication(byte[] bytes) {
        return serializationStrategy.deserialize(bytes, OAuth2Authentication.class);
    }

    /**
     * 反序列化 refresh_token
     * @Author xbx
     * @Date 9:46 2022/7/1
     **/
    private OAuth2RefreshToken deserializeRefreshToken(byte[] bytes) {
        return serializationStrategy.deserialize(bytes, OAuth2RefreshToken.class);
    }

    /**
     * 序列化
     * @Author xbx
     * @Date 9:46 2022/7/1
     **/
    private byte[] serialize(String string) {
        return serializationStrategy.serialize(string);
    }

    /**
     * 反序列化字符串
     * @Author xbx
     * @Date 9:47 2022/7/1
     **/
    private String deserializeString(byte[] bytes) {
        return serializationStrategy.deserializeString(bytes);
    }


    /**
     * 获取 access_token 并存入redis中
     * @Author xbx
     * @Date 9:51 2022/7/1
     **/
    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        //提取密钥,获得access_token
        String key = authenticationKeyGenerator.extractKey(authentication);
        //TODO  是否可以更爱令牌，实现自定义的令牌存储 这个令牌保存的的是token的序列化 key == access_token
        byte[] serializedKey = serializeKey(AUTH_TO_ACCESS + key);
        byte[] bytes = null;
        RedisConnection conn = getConnection();
        try {
            bytes = conn.get(serializedKey);
        } finally {
            conn.close();
        }
        //反序列化 token
        OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
        if (accessToken != null) {
            //获取 身份验证
            OAuth2Authentication storedAuthentication = readAuthentication(accessToken.getValue());
            //如果身份验证 （Authentication）为空或者不存在，则保存新的token
            if ((storedAuthentication == null || !key.equals(authenticationKeyGenerator.extractKey(storedAuthentication)))) {
                // Keep the stores consistent (maybe the same user is
                // represented by this authentication but the details have
                // changed)
                storeAccessToken(accessToken, authentication);
            }

        }
        return accessToken;
    }

    /**
     * 读取身份验证 token中的
     * @Author xbx
     * @Date 10:59 2022/7/1
     **/
    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    /**
     * 读取身份验证 根据access_token redis中的
     * @Author xbx
     * @Date 10:50 2022/7/1
     **/
    @Override
    public OAuth2Authentication readAuthentication(String token) {
        //TODO 自定义 根据token 获取信息
        byte[] bytes = null;
        RedisConnection conn = getConnection();
        try {
            bytes = conn.get(serializeKey(AUTH + token));
        } finally {
            conn.close();
        }
        OAuth2Authentication auth = deserializeAuthentication(bytes);
        return auth;
    }

    /**
     * 读取 refresh_token 在token中
     * @Author xbx
     * @Date 11:04 2022/7/1
     **/
    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    /**
     * 读取 refresh_token 在redis中
     * @Author xbx
     * @Date 11:04 2022/7/1
     **/
    public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
        //TODO 自定义读取 token
        RedisConnection conn = getConnection();
        try {
            byte[] bytes = conn.get(serializeKey(REFRESH_AUTH + token));
            OAuth2Authentication auth = deserializeAuthentication(bytes);
            return auth;
        } finally {
            conn.close();
        }
    }

    /**
     * 存储访问令牌
     * @Author xbx
     * @Date 10:53 2022/7/1
     **/
    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        byte[] serializedAccessToken = serialize(token);
        byte[] serializedAuth = serialize(authentication);
        byte[] accessKey = serializeKey(ACCESS + token.getValue());
        byte[] authKey = serializeKey(AUTH + token.getValue());
        byte[] authToAccessKey = serializeKey(AUTH_TO_ACCESS + authenticationKeyGenerator.extractKey(authentication));
        byte[] approvalKey = serializeKey(UNAME_TO_ACCESS + getApprovalKey(authentication));
        byte[] clientId = serializeKey(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());

        Map<String,Object > tokenJson = new HashMap<>();
        tokenJson.put("access_token",token.getValue());
        tokenJson.put("token_type",token.getTokenType());
        tokenJson.put("refresh_token",token.getRefreshToken());
        tokenJson.put("expires_in",token.getExpiration());
        tokenJson.put("scope",token.getScope());

        byte[] redisTokenValue = serialize(token);



        //TODO 自定义存储到redis中 只存储一个最好
        RedisConnection conn = getConnection();
        conn.set(serializeKey(token.getValue()),serialize(tokenJson));
        try {
            //开启redis 批处理管道
            conn.openPipeline();
            if (springDataRedis_2_0) {
                try {
                    //invoke 调用反射生成Method 方法
                    //这里只的是redis,set方法
                    this.redisConnectionSet_2_0.invoke(conn, accessKey, serializedAccessToken);
                    this.redisConnectionSet_2_0.invoke(conn, authKey, serializedAuth);
                    this.redisConnectionSet_2_0.invoke(conn, authToAccessKey, serializedAccessToken);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                conn.set(accessKey, serializedAccessToken);
                conn.set(authKey, serializedAuth);
                conn.set(authToAccessKey, serializedAccessToken);
            }
            //conn.set(serializedAccessToken,redisTokenValue);
            if (!authentication.isClientOnly()) {
                //sAdd命令将一个或多个成员元素加入到集合中，已经存在于集合的成员元素将被忽略。
                conn.sAdd(approvalKey, serializedAccessToken);
            }

            conn.sAdd(clientId, serializedAccessToken);

            if (token.getExpiration() != null) {
                //设置过期时间
                int seconds = token.getExpiresIn();
                conn.expire(accessKey, seconds);
                conn.expire(authKey, seconds);
                conn.expire(authToAccessKey, seconds);
                conn.expire(clientId, seconds);
                conn.expire(approvalKey, seconds);

                conn.expire(serialize(token.getValue()),seconds);
            }
            OAuth2RefreshToken refreshToken = token.getRefreshToken();
            if (refreshToken != null && refreshToken.getValue() != null) {
                byte[] refresh = serialize(token.getRefreshToken().getValue());
                byte[] auth = serialize(token.getValue());
                byte[] refreshToAccessKey = serializeKey(REFRESH_TO_ACCESS + token.getRefreshToken().getValue());
                byte[] accessToRefreshKey = serializeKey(ACCESS_TO_REFRESH + token.getValue());
                if (springDataRedis_2_0) {
                    try {
                        this.redisConnectionSet_2_0.invoke(conn, refreshToAccessKey, auth);
                        this.redisConnectionSet_2_0.invoke(conn, accessToRefreshKey, refresh);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    conn.set(refreshToAccessKey, auth);
                    conn.set(accessToRefreshKey, refresh);
                }
                if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                    ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
                    Date expiration = expiringRefreshToken.getExpiration();
                    if (expiration != null) {
                        int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
                                .intValue();
                        conn.expire(refreshToAccessKey, seconds);
                        conn.expire(accessToRefreshKey, seconds);
                    }
                }
            }
            conn.closePipeline();
        } finally {
            conn.close();
        }
    }

    private static String getApprovalKey(OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication() == null ? ""
                : authentication.getUserAuthentication().getName();
        return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private static String getApprovalKey(String clientId, String userName) {
        return clientId + (userName == null ? "" : ":" + userName);
    }

    /**
     * 删除token
     * @Author xbx
     * @Date 11:06 2022/7/1
     **/
    @Override
    public void removeAccessToken(OAuth2AccessToken accessToken) {
        removeAccessToken(accessToken.getValue());
    }

    /**
     * 读取access_token  在redis中
     * @Author xbx
     * @Date 11:06 2022/7/1
     **/
    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        //TODO 读取自定义 的token
//        byte[] key = serializeKey(ACCESS + tokenValue);
//        byte[] bytes = null;
//        RedisConnection conn = getConnection();
//        try {
//            bytes = conn.get(key);
//        } finally {
//            conn.close();
//        }
        byte[] key = serializeKey(tokenValue);
        byte[] bytes = null;
        RedisConnection conn = getConnection();
        try {
            conn.get(key);
        }finally {
            conn.close();
        }

        Map map = deserializeMap(bytes);


        OAuth2AccessToken accessToken = deserializeAccessToken(bytes);


        return accessToken;
    }

    /**
     * 删除token  在redis中
     * @Author xbx
     * @Date 11:06 2022/7/1
     **/
    public void removeAccessToken(String tokenValue) {
        //TODO 自定义删除
        byte[] accessKey = serializeKey(ACCESS + tokenValue);
        byte[] authKey = serializeKey(AUTH + tokenValue);
        byte[] accessToRefreshKey = serializeKey(ACCESS_TO_REFRESH + tokenValue);
        byte[] delKey = serializeKey(tokenValue);

        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            conn.del(delKey);
            /*conn.openPipeline();
            conn.get(accessKey);
            conn.get(authKey);
            conn.del(accessKey);
            conn.del(accessToRefreshKey);
            // Don't remove the refresh token - it's up to the caller to do that
            conn.del(authKey);
            List<Object> results = conn.closePipeline();
            byte[] access = (byte[]) results.get(0);
            byte[] auth = (byte[]) results.get(1);

            OAuth2Authentication authentication = deserializeAuthentication(auth);
            if (authentication != null) {
                String key = authenticationKeyGenerator.extractKey(authentication);
                byte[] authToAccessKey = serializeKey(AUTH_TO_ACCESS + key);
                byte[] unameKey = serializeKey(UNAME_TO_ACCESS + getApprovalKey(authentication));
                byte[] clientId = serializeKey(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());
                conn.openPipeline();
                conn.del(authToAccessKey);
                conn.sRem(unameKey, access);
                conn.sRem(clientId, access);
                conn.del(serialize(ACCESS + key));
                conn.closePipeline();
            }*/
        } finally {
            conn.close();
        }
    }

    /**
     * 储存 refresh_token
     * @Author xbx
     * @Date 11:08 2022/7/1
     **/
    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        //TODO 存储refresh_token 是否需要
        byte[] refreshKey = serializeKey(REFRESH + refreshToken.getValue());
        byte[] refreshAuthKey = serializeKey(REFRESH_AUTH + refreshToken.getValue());
        byte[] serializedRefreshToken = serialize(refreshToken);
        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            if (springDataRedis_2_0) {
                try {
                    this.redisConnectionSet_2_0.invoke(conn, refreshKey, serializedRefreshToken);
                    this.redisConnectionSet_2_0.invoke(conn, refreshAuthKey, serialize(authentication));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                conn.set(refreshKey, serializedRefreshToken);
                conn.set(refreshAuthKey, serialize(authentication));
            }
            if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
                Date expiration = expiringRefreshToken.getExpiration();
                if (expiration != null) {
                    int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
                            .intValue();
                    conn.expire(refreshKey, seconds);
                    conn.expire(refreshAuthKey, seconds);
                }
            }
            conn.closePipeline();
        } finally {
            conn.close();
        }
    }

    /**
     * 读取 refresh_token 在redis中
     * @Author xbx
     * @Date 11:08 2022/7/1
     **/
    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        byte[] key = serializeKey(REFRESH + tokenValue);
        byte[] bytes = null;
        RedisConnection conn = getConnection();
        try {
            bytes = conn.get(key);
        } finally {
            conn.close();
        }
        OAuth2RefreshToken refreshToken = deserializeRefreshToken(bytes);
        return refreshToken;
    }

    /**
     * 删除 refresh_token
     * @Author xbx
     * @Date 11:09 2022/7/1
     **/
    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
        removeRefreshToken(refreshToken.getValue());
    }

    /**
     * 删除 refresh_token 在redis中
     * @Author xbx
     * @Date 11:09 2022/7/1
     **/
    public void removeRefreshToken(String tokenValue) {
        byte[] refreshKey = serializeKey(REFRESH + tokenValue);
        byte[] refreshAuthKey = serializeKey(REFRESH_AUTH + tokenValue);
        byte[] refresh2AccessKey = serializeKey(REFRESH_TO_ACCESS + tokenValue);
        byte[] access2RefreshKey = serializeKey(ACCESS_TO_REFRESH + tokenValue);
        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            conn.del(refreshKey);
            conn.del(refreshAuthKey);
            conn.del(refresh2AccessKey);
            conn.del(access2RefreshKey);
            conn.closePipeline();
        } finally {
            conn.close();
        }
    }

    /**
     * 使用刷新令牌删除访问令牌
     * @Author xbx
     * @Date 11:13 2022/7/1
     **/
    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    private void removeAccessTokenUsingRefreshToken(String refreshToken) {
        byte[] key = serializeKey(REFRESH_TO_ACCESS + refreshToken);
        List<Object> results = null;
        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            conn.get(key);
            conn.del(key);
            results = conn.closePipeline();
        } finally {
            conn.close();
        }
        if (results == null) {
            return;
        }
        byte[] bytes = (byte[]) results.get(0);
        String accessToken = deserializeString(bytes);
        if (accessToken != null) {
            removeAccessToken(accessToken);
        }
    }

    /** 1421.57
     * 获取byte数组
     * @Author xbx
     * @Date 11:14 2022/7/1
     **/
    private List<byte[]> getByteLists(byte[] approvalKey, RedisConnection conn) {
        List<byte[]> byteList;
        //获取当前key的集合数量
        Long size = conn.sCard(approvalKey);
        byteList = new ArrayList<byte[]>(size.intValue());
        //获取集合 根据key* 类似模糊查询
        Cursor<byte[]> cursor = conn.sScan(approvalKey, ScanOptions.NONE);
        while(cursor.hasNext()) {
            byteList.add(cursor.next());
        }
        return byteList;
    }

    /**
     * 按客户ID和用户名查找令牌
     * @Author xbx
     * @Date 11:15 2022/7/1
     **/
    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        byte[] approvalKey = serializeKey(UNAME_TO_ACCESS + getApprovalKey(clientId, userName));
        List<byte[]> byteList = null;
        RedisConnection conn = getConnection();
        try {
            byteList = getByteLists(approvalKey, conn);
        } finally {
            conn.close();
        }
        if (byteList == null || byteList.size() == 0) {
            return Collections.<OAuth2AccessToken> emptySet();
        }
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>(byteList.size());
        for (byte[] bytes : byteList) {
            OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
            accessTokens.add(accessToken);
        }
        return Collections.<OAuth2AccessToken> unmodifiableCollection(accessTokens);
    }

    /**
     * 根据客户端id查询令牌
     * @Author xbx
     * @Date 11:16 2022/7/1
     **/
    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        byte[] key = serializeKey(CLIENT_ID_TO_ACCESS + clientId);
        List<byte[]> byteList = null;
        RedisConnection conn = getConnection();
        try {
            byteList = getByteLists(key, conn);
        } finally {
            conn.close();
        }
        if (byteList == null || byteList.size() == 0) {
            return Collections.<OAuth2AccessToken> emptySet();
        }
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>(byteList.size());
        for (byte[] bytes : byteList) {
            OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
            accessTokens.add(accessToken);
        }
        return Collections.<OAuth2AccessToken> unmodifiableCollection(accessTokens);
    }
}
