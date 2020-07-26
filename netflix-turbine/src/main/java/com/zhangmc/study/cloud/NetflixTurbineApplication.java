package com.zhangmc.study.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author zhangMC
 */
@SpringBootApplication
@EnableTurbine
public class NetflixTurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetflixTurbineApplication.class, args);
    }

}
