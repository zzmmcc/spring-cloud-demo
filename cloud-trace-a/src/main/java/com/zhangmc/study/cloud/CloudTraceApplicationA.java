package com.zhangmc.study.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author 张铭传
 */
@SpringBootApplication
public class CloudTraceApplicationA {

    @Autowired
    private ReactorLoadBalancerExchangeFilterFunction reactorLoadBalancerExchangeFilterFunction;


    public static void main(String[] args) {
        SpringApplication.run(CloudTraceApplicationA.class, args);
    }


    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl("http://trace-b")
                .filter(reactorLoadBalancerExchangeFilterFunction)
                .build();
    }

}
