package com.xingbingxuan.blog.gateway.filter;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.xingbingxuan.blog.utils.Result;
import com.xingbingxuan.blog.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author : xbx
 * @date : 2022/4/23 21:32
 */

@Slf4j
public class UserAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<FilterFactoryConfig>implements Ordered {

    private static final String TOKEN = "Authorization";

    public String unAuthorizedJson(Integer code,String message){
        JSON json = new JSONObject();
        json.putByPath("code",code);
        json.putByPath("message",message);
        return json.toString();
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("withParams");
    }

    public UserAuthGatewayFilterFactory() {
        super(FilterFactoryConfig.class);
    }

    @Override
    public GatewayFilter apply(FilterFactoryConfig config) {
        if (config.isWithParams()){
            return (exchange, chain)->{

                log.info("==============权限拦截，获取token验证======================");
                ServerHttpRequest request = exchange.getRequest();
                HttpHeaders headers = request.getHeaders();
                ServerHttpResponse response = exchange.getResponse();
                String token = headers.getFirst(TOKEN);
                log.info("token => "+token);

                //1.token 不存在
                //hasTest 空返回false 不空 true
                if (!StringUtils.hasText(token)){
                    log.info("token 不存在");
                    byte[] bytes = unAuthorizedJson(401,"用户未登录，请先进行登录。。。").getBytes();
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBuffer buffer = response.bufferFactory().wrap(bytes);
                    return response.writeWith(Mono.just(buffer));
                }
                //2.token存在验证其是否过期，以及是否正确
                Boolean aBoolean = TokenUtil.authToken(token);
                if (!aBoolean){
                    log.info("token 已失效");
                    byte[] bytes = unAuthorizedJson(401,"用户登录以过期，请重新登录。。。").getBytes();
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBuffer buffer = response.bufferFactory().wrap(bytes);
                    return response.writeWith(Mono.just(buffer));
                }


                return chain.filter(exchange);
            };
        }else {
            return (exchange, chain)->{
                return chain.filter(exchange);
            };
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
