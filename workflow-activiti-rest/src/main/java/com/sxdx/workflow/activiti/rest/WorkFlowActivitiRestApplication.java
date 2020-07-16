package com.sxdx.workflow.activiti.rest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@ComponentScan("com.sxdx")
@MapperScan("com.sxdx.workflow.activiti.rest.mapper")
public class WorkFlowActivitiRestApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(WorkFlowActivitiRestApplication.class, args);
        System.out.println("启动成功");
    }
}
