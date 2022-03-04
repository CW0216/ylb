package com.yhm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yhm.repository")
public class MedicineApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedicineApplication.class,args);
    }
}
