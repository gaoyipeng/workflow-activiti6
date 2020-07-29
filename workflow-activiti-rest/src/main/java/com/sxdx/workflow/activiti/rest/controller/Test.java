package com.sxdx.workflow.activiti.rest.controller;

import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.util.CommonResponse;
import com.sxdx.workflow.activiti.rest.service.ILeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: workflow-activiti
 * @description:
 * @author: garnett
 * @create: 2020-07-27 22:08
 **/
@Api(value="dd", description="dd")
@RequestMapping("/aa")
@Slf4j
@RestController
public class Test {
    @Autowired
    private ILeaveService leaveService;

    @ApiOperation(value = "获取流程的历史任务",notes = "获取流程的历史任务")
    @GetMapping(value = "/aa")
    public CommonResponse getAll(){
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(leaveService.getAll());
    }
}
