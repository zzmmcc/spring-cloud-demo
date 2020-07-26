package com.zhangmc.study.cloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangMC
 * @className FeignHelloRemote
 * @date 2020/7/25 19:31
 **/
@FeignClient(name = "eureka-producer", fallback = FeignHelloRemoteHystrix.class)
public interface FeignHelloRemote {

    /**
     * feign test
     * @param name 姓名
     * @return .....
     */
    @GetMapping("/hello")
    String hello(@RequestParam(value = "name", required = false) String name);
}
