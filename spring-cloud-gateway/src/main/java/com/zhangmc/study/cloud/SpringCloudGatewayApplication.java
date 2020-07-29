package com.zhangmc.study.cloud;

import com.zhangmc.study.cloud.filter.ElapsedFilter;
import com.zhangmc.study.cloud.filter.ElapsedGatewayFilterFactory;
import com.zhangmc.study.cloud.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author 张铭传
 */
@SpringBootApplication
public class SpringCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    };

    @Bean
    public ElapsedGatewayFilterFactory elapsedGatewayFilterFactory(){
        return new ElapsedGatewayFilterFactory();
    }

//    @Bean
//    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder){
//        return builder.routes()
//                .route(r -> r.path("/consumer/**")
//                        .filters(f -> f.stripPrefix(1)
//                                .addRequestHeader("X-Response-Default-Foo", "Default-Bar")
//                                .filter(new ElapsedFilter())
//                        )
//                        .uri("lb://EUREKA-CONSUMER-FEIGN-HYSTRIX")
//                        .order(0)
//                        .id("stream_consumer_service")
//                ).build();
//    }

}
