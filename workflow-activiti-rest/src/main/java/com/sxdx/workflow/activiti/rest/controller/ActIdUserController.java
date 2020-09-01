package com.sxdx.workflow.activiti.rest.controller;


import com.github.pagehelper.PageInfo;
import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.util.CommonResponse;
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
@Api(value="Activiti用户模块", description="Activiti用户模块")
@RestController
@RequestMapping("/acUser")
@Slf4j
public class ActIdUserController {

    @Autowired
    private IActIdUserService iActIdUserService;
    /**
     * 获取所有人员
     * @param pageNum
     * @param pageSize
     */
    @GetMapping(value = "getAllUser")
    public CommonResponse getAllUser(@RequestParam(value = "pageNum", required = false,defaultValue = "1")
                           @ApiParam(value = "页码" ,required = false)int pageNum,
                                     @RequestParam(value = "pageSize", required = false,defaultValue = "5")
                           @ApiParam(value = "条数" ,required = false)int pageSize){
        PageInfo allUser = iActIdUserService.getAllUser(pageNum, pageSize);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(allUser);
    }
}
