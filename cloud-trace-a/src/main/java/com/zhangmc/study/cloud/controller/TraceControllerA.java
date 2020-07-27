package com.zhangmc.study.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author 张铭传
 * @date 2020/7/27 17:24
 */
@RestController
public class TraceControllerA {

    private final WebClient webClient;

    @Autowired
    public TraceControllerA(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/trace-a")
    public Mono<String> trace(){
        System.out.println("==================== call trace - a =====================");
        return webClient.get().uri("/trace-b").retrieve().bodyToMono(String.class);
    }
}
