package com.sxdx.workflow.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sxdx.workflow.web.mapper")
public class WorkFlowWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkFlowWebApplication.class, args);
    }
}
