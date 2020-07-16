package com.sxdx.workflow.activiti.rest.controller;


import cn.hutool.core.bean.BeanUtil;
import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.exception.base.CommonException;
import com.sxdx.common.util.CommonResponse;
import com.sxdx.workflow.activiti.rest.config.ICustomProcessDiagramGenerator;
import com.sxdx.workflow.activiti.rest.config.WorkflowConstants;
import com.sxdx.workflow.activiti.rest.service.ProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Api(value="流程管理模块", description="流程管理模块")
@RestController
@RequestMapping("/process")
@Slf4j
public class ProcessController  {

    @Autowired
    private ProcessService processService;

    @GetMapping(value = "/read-resource/{pProcessInstanceId}")
    @ApiOperation(value = "获取实时流程图",notes = "获取实时流程图,输出跟踪流程信息")
    public void readResource(@PathVariable("pProcessInstanceId") @ApiParam("流程实例ID (act_hi_procinst表id)") String pProcessInstanceId, HttpServletResponse response)
            throws Exception {
        processService.readResource(pProcessInstanceId,response);
    }

    @PostMapping(value = "/task/list")
    @ApiOperation(value = "获取当前人员待办列表",notes = "获取当前人员待办列表,如果要查询所有，则传all")
    public CommonResponse taskList(@RequestParam(value = "processDefinitionKey", required = true) @ApiParam("流程定义KEY(act_re_procdef表KEY)")String processDefinitionKey, HttpServletRequest request) {
        List<Task> taskList = processService.taskList(processDefinitionKey, request);

        List<Map<String, Object>> customTaskList = new ArrayList<>();
        for (Task task : taskList) {
            customTaskList.add(BeanUtil.beanToMap(task));
        }
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("获取当前人员待办列表成功").data(customTaskList);
    }




    /**
     * 签收任务
     */
    @GetMapping(value = "/task/claim/{id}")
    @ApiOperation(value = "签收任务",notes = "签收任务")
    public CommonResponse claim(@PathVariable("id") @ApiParam("任务id")String taskId, HttpServletRequest request) {
        processService.claim(taskId, request);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("已签收").data("");
    }


    /**
     * 办理任务，提交task，并保存form
     */
    @PostMapping(value = "/task/complete/{taskId}")
    @ApiOperation(value = "办理任务，提交task，并保存form",notes = "办理任务，提交task，并保存form")
    public CommonResponse completeTask(@PathVariable("taskId") String taskId, HttpServletRequest request) {
        processService.completeTask(taskId,request);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("任务办理成功").data(taskId);
    }

    /**
     * 提交启动流程
     */
    @PostMapping(value = "/start-process/{processDefinitionId}")
    @ApiOperation(value = "提交启动流程",notes = "提交启动流程，key需要以fp_开头")
    public CommonResponse submitStartFormAndStartProcessInstance(@PathVariable("processDefinitionId") @ApiParam("act_re_procdef表id")String processDefinitionId,
                                                                 HttpServletRequest request) throws CommonException {
        ProcessInstance processInstance = processService.submitStartFormAndStartProcessInstance(processDefinitionId,request);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("流程启动成功，流程ID：" + processInstance.getId()).data(processInstance.getId());
    }


}
