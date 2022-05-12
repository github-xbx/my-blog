package com.xingbingxuan.blog.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @author : xbx
 * @date : 2022/4/3 10:33
 */
public class RedisUtil {
    private static JedisPool jedisPool;
    private static int index;
    static {
        // ResourceBundle: 专门解析properties配置文件
        // 解析properties配置文件,获取文件中的数据信息
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        int maxTotal = Integer.parseInt(bundle.getString("redis.maxTotal"));
        int maxIdle = Integer.parseInt(bundle.getString("redis.maxIdle"));
        int maxWaitMillis = Integer.parseInt(bundle.getString("redis.maxWaitMillis"));
        String host = bundle.getString("redis.host");
        int port = Integer.parseInt(bundle.getString("redis.port"));

        index = Integer.parseInt(bundle.getString("redis.database.index"));

        // 初始化连接池配置信息
        // 创建连接池配置信息对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数量
        config.setMaxTotal(maxTotal);
        // 设置最大空闲数量
        config.setMaxIdle(maxIdle);
        // 设置最大超时时间
        config.setMaxWaitMillis(maxWaitMillis);
        // 创建连接池对象
        jedisPool = new JedisPool(config, host, port);

    }

    // 提供获取连接的方法
    public static Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        jedis.select(index);
        return jedis;
    }
    // 提供归还连接的方法
    public static void close(Jedis jedis){
        if(jedis!=null){
            jedis.close();
        }
    }


    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return key == null ? null : getJedis().get(key);
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            getJedis().unlink(key);

        }
    }


    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return getJedis().exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */

    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                getJedis().set(key, (String) value);
                getJedis().expire(key,time);
            } else {
                getJedis().set(key, (String) value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 递增+1
     * @param key   键
     */
    public static long incr(String key) {

        return getJedis().incr(key);
    }


    /**
     * 递减 -1
     * @param key   键
     */
    public static long decr(String key) {

        return getJedis().decr(key);
    }

}
