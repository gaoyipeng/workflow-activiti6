package com.sxdx.workflow.activiti.rest.controller;


import com.github.pagehelper.PageInfo;
import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.util.CommonResponse;
import com.sxdx.workflow.activiti.rest.service.IActIdGroupService;
import com.sxdx.workflow.activiti.rest.service.IActIdUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Garnett
 * @since 2020-08-31
 */
@Api(value="Activiti组模块", description="Activiti组模块")
@RestController
@RequestMapping("/acGroup")
@Slf4j
public class ActIdGroupController {
    @Autowired
    private IActIdGroupService iActIdGroupService;
    /**
     * 获取所有组
     * @param pageNum
     * @param pageSize
     */
    @GetMapping(value = "getAllGroup")
    public CommonResponse getAllGroup(@RequestParam(value = "pageNum", required = false,defaultValue = "1")
                                     @ApiParam(value = "页码" ,required = false)int pageNum,
                                     @RequestParam(value = "pageSize", required = false,defaultValue = "5")
                                     @ApiParam(value = "条数" ,required = false)int pageSize){
        PageInfo allUser = iActIdGroupService.getAllGroup(pageNum, pageSize);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(allUser);
    }
}
