package com.sxdx.workflow.activiti.rest.controller;

import com.sxdx.workflow.activiti.rest.entity.Leave;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value="普通表单模块", description="普通表单模块")
@Controller
@RequestMapping("/normalForm")
@Slf4j
public class NormalFormController {

    @GetMapping (value = "/hello")
    public String index(Model model){
        model.addAttribute("name", "JSP");
        return "hello";
    }


}
