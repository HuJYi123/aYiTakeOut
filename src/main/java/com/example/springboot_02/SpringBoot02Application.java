package com.example.springboot_02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@EnableWebMvc
public class SpringBoot02Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot02Application.class, args);
        log.info("项目启动成功");
    }

}
