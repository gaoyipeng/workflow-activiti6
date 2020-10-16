package com.sxdx.workflow.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="demo1", description="demo1")
@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @GetMapping(name = "/r1")
    @ApiOperation(value = "测试1",notes = "测试1")
    public String demo(){
        return "hello";
    }

}
