package com.sxdx.workflow.activiti.rest.controller;

import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.exception.base.CommonException;
import com.sxdx.common.util.CommonResponse;
import com.sxdx.workflow.activiti.rest.service.DynamicFromService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Api(value="动态表单模块", description="动态表单模块")
@RestController
@Slf4j
@RequestMapping("/dynamicForm")
public class DynamicFromController {

    @Autowired
    private DynamicFromService dynamicFromService;
    @Autowired
    private TaskService taskService;


    /**
     * 读取启动流程的表单字段来渲染start form
     */
    @GetMapping(value = "/get-form/start/{reProcdef}")
    @ApiOperation(value = "获取流程启动节点表单信息",notes = "获取流程启动节点表单信息")
    public CommonResponse findStartForm(@PathVariable("reProcdef") @ApiParam("流程定义Id(act_re_procdef表id)") String processDefinitionId) throws Exception {
        Map<String, Object> startForm = dynamicFromService.findStartForm(processDefinitionId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("获取启动节点").data(startForm);
    }

    /**
     * 读取Task的表单
     */
    @GetMapping(value = "/get-form/task/{taskId}")
    @ApiOperation(value = "读取静态表单-task的表单信息",notes = "读取task的表单信息,返回流程中当前操作人节点的表单信息")
    public CommonResponse getTaskFormByTaskId(@PathVariable("taskId") String taskId) throws Exception {
        Map<String, Object> result =  dynamicFromService.getTaskFormByTaskId(taskId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("读取Task的表单成功").data(result);
    }

}
