package com.xingbingxuan.blog.oauth2.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * bean配置类
 * @author : xbx
 * @date : 2022/8/31 22:14
 */
@Configuration
public class AuthServerBeanConfig {

    /**
     * restTemplate bean构造
     **/
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    /**
     * spring security 密码编码器
     **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * redisTemplate 配置bean
     **/
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(factory);
        //key序列化
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //value 序列化
        redisTemplate.setValueSerializer(stringRedisSerializer);
        //value hashmap序列化
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        //key hashmap 序列化
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        return redisTemplate;
    }
}
