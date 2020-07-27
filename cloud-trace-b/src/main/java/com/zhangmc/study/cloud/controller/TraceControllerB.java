package com.zhangmc.study.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author 张铭传
 * @date 2020/7/27 18:14
 */
@RestController
public class TraceControllerB {

    @GetMapping("/trace-b")
    public Mono<String> trace(){
        System.out.println("=============== call trace b============");
        return Mono.just("Trace");
    }
}
