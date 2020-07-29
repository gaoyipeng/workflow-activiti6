package com.sxdx.workflow.activiti.rest.service;

import com.sxdx.workflow.activiti.rest.entity.Leave;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Garnett
 * @since 2020-06-27
 */
public interface ILeaveService extends IService<Leave> {
    Leave startForm(Leave leave);
    List<Leave> getTodoList(String processDefinitionKey, HttpServletRequest request);
    List<Leave> getAll();
}
