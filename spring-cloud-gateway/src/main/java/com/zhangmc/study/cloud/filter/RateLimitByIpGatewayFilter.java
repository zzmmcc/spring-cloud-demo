package com.zhangmc.study.cloud.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张铭传
 * @date 2020/7/29 17:45
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@CommonsLog
public class RateLimitByIpGatewayFilter implements GatewayFilter, Ordered {
    /**
     * 桶的最大容量，即能装载 Token 的最大数量
     */
    private int capacity;
    /**
     * 每次 Token 补充量
     */
    private int refillTokens;
    /**
     * 补充 Token 的时间间隔
     */
    private Duration refillDuration;

    /**
     * token容器
     */
    private final static Map<String, Bucket> CACHE = new ConcurrentHashMap<>();

    private Bucket createBucket(){
        Refill refill = Refill.of(refillTokens, refillDuration);
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
        Bucket bucket = CACHE.computeIfAbsent(ip, k -> createBucket());
        log.debug("IP: " + ip + ", TokenBucket Available Tokens:" + bucket.getAvailableTokens());
        if (bucket.tryConsume(1)){
            return chain.filter(exchange);
        }
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
