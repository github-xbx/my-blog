package com.xingbingxuan.blog.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author : xbx
 * @date : 2022/3/27 17:41
 */
@Configuration
public class RedisConfig {

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
