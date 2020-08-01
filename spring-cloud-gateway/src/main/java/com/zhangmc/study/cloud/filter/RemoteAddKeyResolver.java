package com.zhangmc.study.cloud.filter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * RemoteAddKeyResolver
 *
 * @author zhangMC
 * @date 2020/8/1 12:23
 **/
public class RemoteAddKeyResolver implements KeyResolver {

    public static final String BEAN_NAME = "remoteAddKeyResolver";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
