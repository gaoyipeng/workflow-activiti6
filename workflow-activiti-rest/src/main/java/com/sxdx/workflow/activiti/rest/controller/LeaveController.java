package com.sxdx.workflow.activiti.rest.controller;


import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.util.CommonResponse;
import com.sxdx.common.util.Page;
import com.sxdx.workflow.activiti.rest.entity.Leave;
import com.sxdx.workflow.activiti.rest.service.ILeaveService;
import com.sxdx.workflow.activiti.rest.service.NormalFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.service.ApiListing;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Garnett
 * @since 2020-06-27
 */
@Controller
@RequestMapping("/leave")
@Slf4j
@Api(value="请假模块", description="普通表单-请假模块")
public class LeaveController {

    @Autowired
    private ILeaveService leaveService;


    @GetMapping(value = {"apply"})
    public String createForm(Model model) {
        model.addAttribute("leave", new Leave());
        return "leave/leaveApply";
    }


    @PostMapping(value = {"start"})
    @ResponseBody
    @ApiOperation(value = "普通表单-发起请假流程",notes = "普通表单-发起请假流程")
    public CommonResponse startForm(Leave leave) {
        Leave leave1 = leaveService.startForm(leave);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("成功发起普通表单请假流程").data(leave1);
    }


    @PostMapping(value = "/getTodoList")
    @ApiOperation(value = "获取当前人员待办业务数据",notes = "获取当前人员待办业务数据")
    @ResponseBody
    public CommonResponse getTodoList(@RequestParam(value = "processDefinitionKey", required = true) @ApiParam("流程定义Id(act_re_procdef表KEY)")String processDefinitionKey,
                                      @RequestParam(value = "pageNum", required = false,defaultValue = "1")
                                      @ApiParam(value = "页码" ,required = false)int pageNum,
                                      @RequestParam(value = "pageSize", required = false,defaultValue = "10")
                                          @ApiParam(value = "条数" ,required = false)int pageSize){
        Page page = leaveService.getTodoList(processDefinitionKey, pageNum,pageSize);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(page);
    }
}
