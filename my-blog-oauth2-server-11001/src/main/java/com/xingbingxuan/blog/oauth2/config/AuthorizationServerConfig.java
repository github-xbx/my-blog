package com.xingbingxuan.blog.oauth2.config;

import com.xingbingxuan.blog.oauth2.code.AuthorizationCodeServicesImpl;
import com.xingbingxuan.blog.oauth2.token.MyTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
/**
 * 功能描述:
 * <p>授权服务器配置</p>
 *
 * @author : xbx
 * @date : 2023/4/2 9:21
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 替换oauth2默认的数据源配置，使用我们配置的
     * @Primary 注解的意思以此为准
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }


    /**
     * token 也存储到数据库 redis中
     * @return
     */
    @Bean
    public TokenStore tokenStore(){

        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);

//        MyRedisTokenStore redisTokenStore = new MyRedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("user:");

        return redisTokenStore;
        //return new JdbcTokenStore(dataSource());
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单登录
        // 认证服务器安全配置
        security.allowFormAuthenticationForClients();
        //开启token验证 "/oauth/check_token" 接口
        security.checkTokenAccess("permitAll()");
        // 允许资源服务访问认证服务获取token算法和签名密钥的接口。
        security.tokenKeyAccess("permitAll()");

    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        /*clients.inMemory()
                .withClient("client-app")
                .secret(passwordEncoder.encode("123456"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(86400);*/


        clients.withClientDetails(new JdbcClientDetailsService(dataSource()));

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        DefaultAccessTokenConverter myAccessTokenConverter = new DefaultAccessTokenConverter();

        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                //修改token生成策略
                .tokenServices(tokenService(endpoints))
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .accessTokenConverter(myAccessTokenConverter)
                .authorizationCodeServices(authorizationCodeServices())
                .reuseRefreshTokens(false);


    }
    /**
     * 自定义token形式设置
     * @Author xbx
     **/
    private MyTokenServices tokenService(AuthorizationServerEndpointsConfigurer endpoints){
        MyTokenServices tokenServices = new MyTokenServices();
        tokenServices.setTokenStore(tokenStore());
        //支持刷新token
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        return tokenServices;

    }

    /**
     * 自定义授权code
     * @Author xbx
     **/
    private AuthorizationCodeServices authorizationCodeServices() {
        return new AuthorizationCodeServicesImpl(dataSource());
    }



}

