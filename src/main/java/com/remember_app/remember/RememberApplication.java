package com.remember_app.remember;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.remember_app.remember.mapper")
public class RememberApplication {
    public static void main(String[] args) {
        SpringApplication.run(RememberApplication.class, args);
    }

}
