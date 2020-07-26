package com.zhangmc.study.cloud.coltroller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * HelloController
 *
 * @author zhangMC
 * @date 2020/7/25 17:08
 **/
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Value("${config.producer.instanceId:0}")
    private Integer instanceId;

    @GetMapping("")
    public String hello(@RequestParam(value = "name", required = false) String name){
        return name + "_________" + new Date() + "___________________  [  " + instanceId +" ] ";
    }
}
