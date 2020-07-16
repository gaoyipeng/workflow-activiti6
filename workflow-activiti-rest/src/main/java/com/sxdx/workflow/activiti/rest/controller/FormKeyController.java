package com.sxdx.workflow.activiti.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value="外置表单模块", description="外置表单模块")
@RestController
@Slf4j
@RequestMapping("/formKey")
public class FormKeyController {

    @Autowired
    private FormService formService;


    @GetMapping(value = "/get-form/start/{processDefinitionId}")
    @ApiOperation(value = "获取外置表单-开始表单的内容",notes = "获取外置表单-开始表单的内容")
    public Object findStartForm(@PathVariable("processDefinitionId") @ApiParam("流程定义id") String processDefinitionId) throws Exception {
        // 根据流程定义ID读取外置表单
        Object startForm = formService.getRenderedStartForm(processDefinitionId);
        return startForm;
    }

    /**
     * 读取Task的表单
     */
    @GetMapping(value = "/get-form/task/{taskId}")
    @ApiOperation(value = "获取外置表单-读取Task的表单",notes = "获取外置表单-读取Task的表单")
    public Object findTaskForm(@PathVariable("taskId") @ApiParam("任务id")String taskId) throws Exception {
        Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
        return renderedTaskForm;
    }

}
