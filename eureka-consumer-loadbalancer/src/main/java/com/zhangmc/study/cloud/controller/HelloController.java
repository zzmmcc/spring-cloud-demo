package com.zhangmc.study.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * LoadBalanceController
 *
 * @author zhangMC
 * @date 2020/7/25 17:26
 **/
@RestController
@RequestMapping("/hello")
public class HelloController {

    public final RestTemplate restTemplate;
    public final LoadBalancerClient loadBalancerClient;

    @Autowired
    public HelloController(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient) {
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
    }

    /**
     * TODO ： 使用此方式需要将启动类中的@LoadBalanced去除
    @GetMapping("/load")
    public String hello_load(@RequestParam(value = "name", required = false) String name){
        name = "LoadBalancerClient: \n" + name;
        ServiceInstance instance = loadBalancerClient.choose("eureka-producer");
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/hello/?name=" + name;
        return restTemplate.getForObject(url, String.class);
    }
     */

    /**
     * 使用此方式需要加上@LoadBalanced去除
     * @param name 姓名
     * @return .....
     */
    @GetMapping("/ribbon")
    public String hello_ribbon(@RequestParam(value = "name", required = false) String name){
        name = "Ribbon: \t" + name;
        String url = "http://eureka-producer/hello/?name=" + name;
        return restTemplate.getForObject(url, String.class);
    }
}
