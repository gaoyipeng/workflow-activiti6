package com.sxdx.workflow.activiti.rest.service;


import com.sxdx.common.exception.base.CommonException;
import com.sxdx.common.util.Page;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface ProcessService {
    void readResource(String pProcessInstanceId, HttpServletResponse response) throws Exception ;
    Page taskList(String processDefinitionKey, int pageNum, int pageSize);
    void claim(String taskId, HttpServletRequest request);
    void completeTask(String taskId,HttpServletRequest request);
    ProcessInstance submitStartFormAndStartProcessInstance(String processDefinitionId, HttpServletRequest request) throws CommonException;
    ProcessInstance messageStartEventInstance(String messageId, HttpServletRequest request) throws CommonException;
    void signalStartEventInstance(String signalId, HttpServletRequest request) throws CommonException;
}
