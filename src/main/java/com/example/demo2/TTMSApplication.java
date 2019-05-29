package com.example.demo2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = "com.example.demo2.dao")
public class TTMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(TTMSApplication.class, args);
    }

}
