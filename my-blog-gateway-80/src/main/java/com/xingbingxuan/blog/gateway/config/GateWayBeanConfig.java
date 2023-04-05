package com.xingbingxuan.blog.gateway.config;

import com.xingbingxuan.blog.gateway.filter.AdminAuthGatewayFilterFactory;
import com.xingbingxuan.blog.gateway.filter.GatewayGlobalFilter;
import com.xingbingxuan.blog.gateway.filter.UserAuthGatewayFilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author : xbx
 * @date : 2022/4/26 20:48
 */
@Configuration
public class GateWayBeanConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    /**
     * 功能描述:
     * <p>用户登录的过滤器bean注入到spring容器</p>
     *
     * @return : com.xingbingxuan.blog.gateway.filter.UserAuthGatewayFilterFactory
     * @author : xbx
     * @date : 2022/4/26 21:13
     */
    @Bean
    public UserAuthGatewayFilterFactory userAuthGatewayFilterFactory(){
        return new UserAuthGatewayFilterFactory();
    }

    /**
     * 功能描述:
     * <p>管理云过滤工厂bean 注入到spring容器中</p>
     *
     * @return : com.xingbingxuan.blog.gateway.filter.AdminAuthGatewayFilterFactory
     * @author : xbx
     * @date : 2022/5/11 22:49
     */
    @Bean
    public AdminAuthGatewayFilterFactory adminAuthGatewayFilterFactory(){
        return new AdminAuthGatewayFilterFactory();
    }

    /**
     * 功能描述:
     * <p>全局的过滤器bean注入到spring容器</p>
     *
     * @return : com.xingbingxuan.blog.gateway.filter.GatewayGlobalFilter
     * @author : xbx
     * @date : 2022/4/26 21:13
     */
    @Bean
    public GatewayGlobalFilter gatewayGlobalFilter(){
        return new GatewayGlobalFilter();
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

    @Bean
    public TokenStore tokenStore(){
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);

//        MyRedisTokenStore redisTokenStore = new MyRedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("user:");

        return redisTokenStore;
    }


}
