package com.xingbingxuan.blog.test;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import com.xingbingxuan.blog.utils.CommonTool;
import com.xingbingxuan.blog.utils.DateTool;
import com.xingbingxuan.blog.utils.TokenUtil;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author : xbx
 * @date : 2022/4/3 22:14
 */
public class CommonTest {


    public static void main(String[] args) throws InterruptedException, IOException {


        List<String> thisWeekTime = DateTool.getThisWeekTime();

        System.out.println(thisWeekTime);


//        String token = TokenUtil.generateToken("admin");
//        System.out.println(token);
//
//        String content = TokenUtil.getTokenContent(token);
//        System.out.println(content);
//
//        String substring = content.substring(0, content.indexOf(":"));
//        System.out.println(substring);

//        Properties properties = CommonTool.getPropertiesContent("key.properties");
//        String publicKey = properties.getProperty("publicKey");
//
//        String privateKey = properties.getProperty("privateKey");
//
//        System.out.println(privateKey);
//        System.out.println(publicKey);

//        KeyPair pair = SecureUtil.generateKeyPair("RSA");
//        String privateKey = Base64.encode(pair.getPrivate().getEncoded());
//        System.out.println("私钥\t" + privateKey);
//        String publicKey = Base64.encode(pair.getPublic().getEncoded());
//        System.out.println("公钥\t" + publicKey);


      /*  //连接本地的 Redis 服务
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());

        RedisDS.create().getJedis();
        //连接池配置对象,包含了很多默认配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //初始化Jedis连接池，通常来讲JedisPool是单例的
        JedisPool jedisPool = new JedisPool(poolConfig, "119.23.226.29", 6379);*/
    }
}
