package com.sxdx.workflow.activiti.rest.service.impl;

import com.sxdx.workflow.activiti.rest.service.NormalFormService;
import org.activiti.engine.*;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NormalFormServiceImpl implements NormalFormService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private FormService formService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;

    /**
     * 启动流程
     * @param processDefinitionKey 流程定义key
     * @param businessKey 业务数据id
     * @param variables  表单字段，此处为null,因为普通表单字段存放在单独的表中
     * @return
     */
    @Override
    public ProcessInstance startWorkflow(String processDefinitionKey, String businessKey, Map<String, Object> variables) {

        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId("kafeitu");

            processInstance = runtimeService.startProcessInstanceByKey("leave", businessKey, variables);
            String processInstanceId = processInstance.getId();

        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return processInstance;
    }




}
