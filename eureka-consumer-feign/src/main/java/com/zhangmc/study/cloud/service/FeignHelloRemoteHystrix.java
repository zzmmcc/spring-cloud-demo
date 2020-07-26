package com.zhangmc.study.cloud.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FeignHelloRemoteHystrix
 *
 * @author zhangMC
 * @date 2020/7/25 21:00
 **/
@Component
public class FeignHelloRemoteHystrix implements FeignHelloRemote {
    @Override
    public String hello(@RequestParam(value = "name", required = false) String name) {
        return "Hystrix FallBack!";
    }
}
