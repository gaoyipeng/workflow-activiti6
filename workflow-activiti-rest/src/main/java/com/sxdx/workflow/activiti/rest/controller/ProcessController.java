package com.sxdx.workflow.activiti.rest.controller;


import cn.hutool.core.bean.BeanUtil;
import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.exception.base.CommonException;
import com.sxdx.common.util.CommonResponse;
import com.sxdx.common.util.Page;
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

    @GetMapping(value = "/readResource/{pProcessInstanceId}")
    @ApiOperation(value = "获取实时流程图",notes = "获取实时流程图,输出跟踪流程信息")
    public void readResource(@PathVariable("pProcessInstanceId") @ApiParam("流程实例ID (act_hi_procinst表id)") String pProcessInstanceId, HttpServletResponse response)
            throws Exception {
        processService.readResource(pProcessInstanceId,response);
    }

    @PostMapping(value = "/task/list")
    @ApiOperation(value = "获取当前人员待办列表",notes = "获取当前人员待办列表,如果要查询所有，则传all")
    public CommonResponse taskList(@RequestParam(value = "processDefinitionKey", required = true,defaultValue = "all") @ApiParam("流程定义KEY(act_re_procdef表KEY)")String processDefinitionKey,
                                   HttpServletRequest request,
                                   @RequestParam(value = "pageNum", required = false,defaultValue = "1")@ApiParam(value = "页码" ,required = false)int pageNum,
                                   @RequestParam(value = "pageSize", required = false,defaultValue = "10")@ApiParam(value = "条数" ,required = false)int pageSize) {
        Page page = processService.taskList(processDefinitionKey, pageNum,pageSize);

        List<Map<String, Object>> customTaskList = new ArrayList<>();
        List<Task> taskList = (List<Task>) page.getList();
        for (Task task : taskList) {
            customTaskList.add(BeanUtil.beanToMap(task));
        }
        page.setList(customTaskList);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("获取当前人员待办列表成功").data(page);
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
    @PostMapping(value = "/startProcess/{processDefinitionId}")
    @ApiOperation(value = "提交启动流程",notes = "提交启动流程，key需要以fp_开头")
    public CommonResponse submitStartFormAndStartProcessInstance(@PathVariable( value = "processDefinitionId",required = true) @ApiParam(name = "act_re_procdef表id",required = true)String processDefinitionId,
                                                                 HttpServletRequest request) throws CommonException {
        ProcessInstance processInstance = processService.submitStartFormAndStartProcessInstance(processDefinitionId,request);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("流程启动成功，流程ID：" + processInstance.getId()).data(processInstance.getId());
    }

    /**
     * 消息启动流程
     */
    @PostMapping(value = "/messageStartEventInstance/{messageName}")
    @ApiOperation(value = "消息启动流程",notes = "发起启动节点是消息启动类型的流程，key需要以fp_开头")
    public CommonResponse messageStartEventInstance(@PathVariable("messageName") @ApiParam("消息定义的名称")String messageName,
                                                                 HttpServletRequest request) throws CommonException {
        ProcessInstance processInstance = processService.messageStartEventInstance(messageName,request);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("流程启动成功，流程ID：" + processInstance.getId()).data(processInstance.getId());
    }

    /**
     * 消息触发
     */
    @PostMapping(value = "/messageEventReceived/{messageName}")
    @ApiOperation(value = "消息触发",notes = "消息触发，表单元素key需要以fp_开头")
    public CommonResponse messageEventReceived(@PathVariable(value = "messageName",required = true) @ApiParam(value = "消息定义的名称",required = true)String messageName,
                                               @RequestParam(value = "executionId",required = false) @ApiParam(value = "执行ID",required = false)String executionId,
                                                    HttpServletRequest request) throws CommonException {
        processService.messageEventReceived(messageName,executionId,request);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("消息触发成功");
    }

    /**
     * 信号触发
     * 可以用来发起启动节点是信号启动类型的流程，也可以用来触发信号边界事件，表单元素key需要以fp_开头
     */
    @PostMapping(value = "/signalStartEventInstance/{signalName}")
    @ApiOperation(value = "信号触发",notes = "可以用来发起启动节点是信号启动类型的流程，也可以用来触发信号边界事件，表单元素key需要以fp_开头")
    public CommonResponse signalStartEventInstance(@PathVariable(value = "signalName",required = true) @ApiParam(value = "信号定义的名称",required = true)String signalName,
                                                   @RequestParam(value = "executionId",required = false) @ApiParam(value = "执行ID",required = false)String executionId,
                                                   HttpServletRequest request) {
        processService.signalStartEventInstance(signalName,executionId,request);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("信号触发成功");
    }

    /**
     * 获取信号事件的执行列表
     */
    @PostMapping(value = "/getSignalEventSubscription/{processInstanceId}/{signalName}")
    @ApiOperation(value = "获取某一信号事件的所有执行",notes = "获取某一信号事件的所有执行")
    public CommonResponse signalEventSubscriptionName(@RequestParam(value = "pageNum", required = true,defaultValue = "1")@ApiParam(value = "页码" ,required = true)int pageNum,
                                                      @RequestParam(value = "pageSize", required = true,defaultValue = "10")@ApiParam(value = "条数" ,required = true)int pageSize,
                                                      @PathVariable(value ="processInstanceId", required = false) @ApiParam(value = "流程实例",required = false)String processInstanceId,
                                                      @PathVariable(value ="signalName", required = true) @ApiParam(value = "信号定义的名称",required = true)String signalName)  {
        Page page = processService.signalEventSubscriptionName(pageNum, pageSize, signalName, processInstanceId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(page);
    }

    /**
     * 获取消息事件的执行列表
     */
    @PostMapping(value = "/getMessageEventSubscription/{processInstanceId}/{messageName}")
    @ApiOperation(value = "获取某一消息事件的所有执行",notes = "获取某一消息事件的所有执行")
    public CommonResponse messageEventSubscriptionName(@RequestParam(value = "pageNum", required = true,defaultValue = "1")@ApiParam(value = "页码" ,required = true)int pageNum,
                                                      @RequestParam(value = "pageSize", required = true,defaultValue = "10")@ApiParam(value = "条数" ,required = true)int pageSize,
                                                      @PathVariable(value ="processInstanceId", required = false) @ApiParam(value = "流程实例",required = false)String processInstanceId,
                                                      @PathVariable(value ="messageName", required = true) @ApiParam(value = "消息定义的名称",required = true)String messageName)  {
        Page page = processService.messageEventSubscriptionName(pageNum, pageSize, messageName, processInstanceId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(page);
    }

    @DeleteMapping (value = "/deleteProcessInstance")
    @ApiOperation(value = "删除流程实例",notes = "删除流程实例")
    public CommonResponse deleteProcessInstance(@RequestParam(value = "processInstanceId", required = true)@ApiParam(value = "流程实例Id" ,required = true)String processInstanceId,
                                      @RequestParam(value = "deleteReason", required = false,defaultValue = "")@ApiParam(value = "原因" ,required = false)String deleteReason){
        processService.deleteProcessInstance(processInstanceId,deleteReason);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("删除流程实例成功");
    }


}
