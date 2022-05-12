package com.xingbingxuan.blog.gateway.filter;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.xingbingxuan.blog.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author : xbx
 * @date : 2022/5/11 22:15
 */
@Slf4j
public class AdminAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<FilterFactoryConfig> {

    private static final String TOKEN = "Authorization";

    public String unAuthorizedJson(Integer code, String message) {
        JSON json = new JSONObject();
        json.putByPath("code", code);
        json.putByPath("message", message);
        return json.toString();
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("withParams");
    }

    public AdminAuthGatewayFilterFactory() {
        super(FilterFactoryConfig.class);
    }

    @Override
    public GatewayFilter apply(FilterFactoryConfig config) {

        if (config.isWithParams()) {
            //参数为真，进行过滤
            return (exchange, chain) -> {
                log.info("==============管理员请求，权限拦截，获取token验证======================");
                ServerHttpRequest request = exchange.getRequest();
                HttpHeaders headers = request.getHeaders();
                ServerHttpResponse response = exchange.getResponse();
                String token = headers.getFirst(TOKEN);
                log.info("token => " + token);

                if (!StringUtils.hasText(token)){
                    log.info("token 为空！！！");
                    byte[] bytes = unAuthorizedJson(401, "没有权限").getBytes();
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBuffer buffer = response.bufferFactory().wrap(bytes);
                    return response.writeWith(Mono.just(buffer));
                }
                //获取token中的内容
                String tokenContent = TokenUtil.getTokenContent(token);
                //管理员用户名
                String adminName = tokenContent.substring(0, tokenContent.indexOf(":"));
                //获取redis key
                String key = "admin:token:"+adminName;

                if (!StringUtils.hasText(key)) {
                    log.info("token 不存在");
                    byte[] bytes = unAuthorizedJson(401, "管理员账户未登录，请先进行登录。。。").getBytes();
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBuffer buffer = response.bufferFactory().wrap(bytes);
                    return response.writeWith(Mono.just(buffer));
                }
                //2.token存在验证其是否过期，以及是否正确
                Boolean aBoolean = TokenUtil.authToken(key);
                if (!aBoolean) {
                    log.info("token 已失效");
                    byte[] bytes = unAuthorizedJson(401, "管理员账户登录以过期，请重新登录。。。").getBytes();
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBuffer buffer = response.bufferFactory().wrap(bytes);
                    return response.writeWith(Mono.just(buffer));
                }


                return chain.filter(exchange);
            };
        } else {
            //否则不进行过滤
            return (exchange, chain) -> {
                return chain.filter(exchange);
            };
        }

    }
}

