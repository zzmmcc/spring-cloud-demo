package com.zhangmc.study.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * HelloController
 *
 * @author zhangMC
 * @date 2020/7/26 16:59
 **/
@RestController
@RefreshScope
public class HelloController {

    @Value("${info.profile:error}")
    private String profile;

    @GetMapping("/info")
    public Mono<String> hello() {
        return Mono.justOrEmpty(profile);
    }
}
