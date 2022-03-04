package com.yhm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication_02 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication_02.class,args);
    }
}
