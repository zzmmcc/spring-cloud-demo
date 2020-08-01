package com.zhangmc.study.cloud.filter;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * RateLimitByCpuGatewayFilter
 *
 * @author zhangMC
 * @date 2020/8/1 14:04
 **/
@CommonsLog
@Component
public class RateLimitByCpuGatewayFilter implements GatewayFilter, Ordered {

    @Autowired
    private MetricsEndpoint metricsEndpoint;
    private static final String METRICS_NAME = "system.cpu.usage";
    private static final Double MAX_USAGE = 0.50D;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Double systemCpuUsage = metricsEndpoint.metric(METRICS_NAME, null)
                .getMeasurements().stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(MetricsEndpoint.Sample::getValue)
                .filter(Double::isFinite)
                .orElse(0.0D);
        boolean ok = systemCpuUsage < MAX_USAGE;

        log.info(METRICS_NAME  + ": " + systemCpuUsage + " ok: " + ok);
        if (!ok){
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
       return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1001;
    }
}
