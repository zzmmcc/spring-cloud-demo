package com.zhangmc.study.cloud;

import com.zhangmc.study.cloud.filter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

/**
 * @author 张铭传
 */
@SpringBootApplication
public class SpringCloudGatewayApplication {
    private final  RateLimitByCpuGatewayFilter rateLimitByCpuGatewayFilter;

    @Autowired
    public SpringCloudGatewayApplication(RateLimitByCpuGatewayFilter rateLimitByCpuGatewayFilter) {
        this.rateLimitByCpuGatewayFilter = rateLimitByCpuGatewayFilter;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }



    @Bean(name = RemoteAddKeyResolver.BEAN_NAME)
    public RemoteAddKeyResolver remoteAddKeyResolver(){
        return new RemoteAddKeyResolver();
    }

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    };

    @Bean
    public ElapsedGatewayFilterFactory elapsedGatewayFilterFactory(){
        return new ElapsedGatewayFilterFactory();
    }

    @Bean
    public RouteLocator limitRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/cpuLimiter/consumer/**")
                        .filters(f -> f.stripPrefix(2)
                                .filter(rateLimitByCpuGatewayFilter)
                        )
                        .uri("lb://EUREKA-CONSUMER-FEIGN-HYSTRIX")
                        .order(0)
                        .id("cpu_limiter_consumer_service")
                ).build();
    }

    /**
     * //网关
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/consumer/**")
                        .filters(f -> f.stripPrefix(1)
                                .addRequestHeader("X-Response-Default-Foo", "Default-Bar")
                                .filter(new ElapsedFilter())
                        )
                        .uri("lb://EUREKA-CONSUMER-FEIGN-HYSTRIX")
                        .order(0)
                        .id("stream_consumer_service")
                ).build();
    }

    /**
     * ip限流
     */
    @Bean
    public RouteLocator IplimitRouteLocator(RouteLocatorBuilder builder){
    return builder.routes()
            .route(r -> r.path("/throttle/consumer/**")
                    .filters(f -> f.stripPrefix(2)
                            .filter(new RateLimitByIpGatewayFilter(10, 1, Duration.ofSeconds(1)))
                    )
                    .uri("lb://EUREKA-CONSUMER-FEIGN-HYSTRIX")
                    .order(0)
                    .id("throttle_consumer_service")
            ).build();
}

}
