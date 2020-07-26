package com.zhangmc.study.cloud.controller;

import com.zhangmc.study.cloud.service.FeignHelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FeignHelloController
 *
 * @author zhangMC
 * @date 2020/7/25 19:28
 **/
@RestController
@RequestMapping("/hello")
public class FeignHelloController {
    private final FeignHelloRemote feignHelloRemote;

    @Autowired
    public FeignHelloController(FeignHelloRemote feignHelloRemote) {
        this.feignHelloRemote = feignHelloRemote;
    }

    @GetMapping(value = "{name}")
    public String hello(@PathVariable String name){
        return feignHelloRemote.hello("FeignClient:" + name);
    }
}
