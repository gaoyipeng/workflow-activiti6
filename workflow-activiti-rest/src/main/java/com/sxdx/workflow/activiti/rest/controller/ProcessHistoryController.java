package com.sxdx.workflow.activiti.rest.controller;

import com.github.pagehelper.PageInfo;
import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.util.CommonResponse;
import com.sxdx.common.util.Page;
import com.sxdx.workflow.activiti.rest.service.ProcessHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="流程历史管理模块", description="流程历史管理模块（历史流程实例、历史活动、历史流程变量）")
@RestController
@RequestMapping("/processHistory")
@Slf4j
public class ProcessHistoryController {
    @Autowired
    private ProcessHistoryService processHistoryService;

    @ApiOperation(value = "获取流程的历史任务",notes = "获取流程的历史任务")
    @GetMapping (value = "/getHistoryTaskList")
    public CommonResponse getHistoryTaskList(@RequestParam(value = "finished", required = false,defaultValue = "2")
                                                 @ApiParam(value = "是否已完成( 0：已完成的任务  1：未完成的任务 2：全部任务（默认）)" ,required = false)String finished,
                                             @RequestParam(value = "processInstanceId", required = true)
                                                @ApiParam(value = "流程实例ID" ,required = true)String processInstanceId){
        List<HistoricTaskInstance> list = processHistoryService.getHistoryTaskList(finished, processInstanceId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(list);
    }

    @ApiOperation(value = "获取流程的历史活动",notes = "获取流程的历史活动")
    @GetMapping (value = "/getHistoryActInstanceList")
    public CommonResponse getHistoryActInstanceList(@RequestParam(value = "finished", required = false,defaultValue = "2")
                                                        @ApiParam(value = "是否已完成( 0：已完成的任务  1：未完成的任务 2：全部任务（默认）)" ,required = false)String finished,
                                                    @RequestParam(value = "processInstanceId", required = true)
                                                        @ApiParam(value = "流程实例ID" ,required = true)String processInstanceId){
        List<HistoricActivityInstance> list = processHistoryService.getHistoryActInstanceList(finished, processInstanceId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(list);
    }

    @ApiOperation(value = "获取流程历史流程变量",notes = "获取流程历史流程变量")
    @GetMapping (value = "/getHistoryProcessVariables")
    public CommonResponse getHistoryProcessVariables(@RequestParam(value = "processInstanceId", required = true)
                                                    @ApiParam(value = "流程实例ID" ,required = true)String processInstanceId){
        List<HistoricVariableInstance> list = processHistoryService.getHistoryProcessVariables(processInstanceId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(list);
    }

    @ApiOperation(value = "获取已归档的流程实例",notes = "获取已归档的流程实例")
    @GetMapping (value = "/getFinishedInstanceList")
    public CommonResponse getFinishedInstanceList(@RequestParam(value = "processDefinitionId", required = true)
                                                      @ApiParam(value = "流程定义ID" ,required = true)String processDefinitionId,
                                                  @RequestParam(value = "pageNum", required = false,defaultValue = "1")
                                                    @ApiParam(value = "页码" ,required = false)int pageNum,
                                                  @RequestParam(value = "pageSize", required = false,defaultValue = "10")
                                                      @ApiParam(value = "条数" ,required = false)int pageSize){
        Page list = processHistoryService.getFinishedInstanceList(pageNum,  pageSize,processDefinitionId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(list);
    }

    @ApiOperation(value = "获取历史流程实例",notes = "获取历史流程实例（所有已发起的流程）")
    @GetMapping (value = "/queryHistoricInstance")
    public CommonResponse queryHistoricInstance(@RequestParam(value = "processDefinitionId", required = true)
                                                    @ApiParam(value = "流程定义ID" ,required = true)String processDefinitionId,
                                                @RequestParam(value = "pageNum", required = false,defaultValue = "1")
                                                  @ApiParam(value = "页码" ,required = false)int pageNum,
                                                @RequestParam(value = "pageSize", required = false,defaultValue = "10")
                                                  @ApiParam(value = "条数" ,required = false)int pageSize){
        Page list = processHistoryService.queryHistoricInstance( pageNum,  pageSize,processDefinitionId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(list);
    }


}
