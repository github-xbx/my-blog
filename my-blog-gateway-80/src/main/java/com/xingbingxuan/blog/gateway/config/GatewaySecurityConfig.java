package com.xingbingxuan.blog.gateway.config;

import com.xingbingxuan.blog.gateway.oauth2.TokenAccessManager;
import com.xingbingxuan.blog.gateway.oauth2.TokenReactiveAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

/**
 * @author : xbx
 * @date : 2023/4/5 11:17
 */
@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {



    private static final String MAX_AGE = "18000L";

    @Autowired
    private TokenAccessManager tokenAccessManager;


    @Autowired
    private TokenReactiveAuthenticationManager tokenReactiveAuthenticationManager;

    @Bean
    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception{
        //token管理器

        //认证过滤器
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(tokenReactiveAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());

        http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().access(tokenAccessManager)
                .and()
                //oauth2认证过滤器
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }



}
