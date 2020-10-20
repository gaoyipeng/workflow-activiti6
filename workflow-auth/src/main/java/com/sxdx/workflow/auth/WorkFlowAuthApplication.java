package com.sxdx.workflow.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sxdx.workflow.auth.mapper")
public class WorkFlowAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkFlowAuthApplication.class, args);
    }
}
