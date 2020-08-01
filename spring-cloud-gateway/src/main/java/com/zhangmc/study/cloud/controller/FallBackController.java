package com.zhangmc.study.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FallBackController
 *
 * @author zhangMC
 * @date 2020/8/1 16:34
 **/
@RestController
public class FallBackController {

    @GetMapping("/fallback")
    public String fallBack(){
        return "fallback method from gateway";
    }
}
