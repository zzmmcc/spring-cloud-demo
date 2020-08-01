package com.zhangmc.study.cloud;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangMC
 */
@SpringBootApplication
@EnableAdminServer
public class CloudAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudAdminServerApplication.class, args);
    }

}
