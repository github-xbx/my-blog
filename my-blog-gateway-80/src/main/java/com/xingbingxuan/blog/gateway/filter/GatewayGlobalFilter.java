package com.xingbingxuan.blog.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author : xbx
 * @date : 2022/4/26 20:19
 */

@Slf4j
public class GatewayGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("======================GateWay全局过滤器=======================");
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        log.info(uri.getScheme()+":"+uri.getRawSchemeSpecificPart());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
