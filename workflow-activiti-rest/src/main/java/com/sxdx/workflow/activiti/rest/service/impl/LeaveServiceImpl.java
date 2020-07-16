package com.sxdx.workflow.activiti.rest.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxdx.common.config.GlobalConfig;
import com.sxdx.workflow.activiti.rest.entity.Leave;
import com.sxdx.workflow.activiti.rest.mapper.LeaveMapper;
import com.sxdx.workflow.activiti.rest.service.ILeaveService;
import com.sxdx.workflow.activiti.rest.service.NormalFormService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Garnett
 * @since 2020-06-29
 */
@Service
@Slf4j
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements ILeaveService {

    private static String  PROCESSDEFINITIONKEY = "leave";
    @Autowired
    private NormalFormService normalFormService;
    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    @Transactional
    @Override
    public Leave startForm(Leave leave) {

        leave.setUserId("kafeitu");
        leave.setApplyTime(LocalDateTime.now());
        //TODO 保存业务数据
        leaveMapper.insert(leave);
        //TODO 发起流程,并建立关联关系
        Map<String, Object> variables = new HashMap<String, Object>();
        ProcessInstance processInstance = normalFormService.startWorkflow(PROCESSDEFINITIONKEY, leave.getId().toString(), variables);
        String processInstanceId = processInstance.getId();
        leave.setProcessInstanceId(processInstanceId);
        leaveMapper.updateById(leave);
        log.debug("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[]{"leave", leave.getId(), processInstanceId, variables});
        return leave;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Leave> getTodoList(String processDefinitionKey, HttpServletRequest request) {
        List<Leave> results = new ArrayList<Leave>();

        //TODO 此处应该添加获取当前操作人的代码,先写死用 leaderuser。
        UserQueryImpl user = new UserQueryImpl();
        user = (UserQueryImpl)identityService.createUserQuery().userId(GlobalConfig.getOperator());

        List<Task> tasks = new ArrayList<Task>();

        tasks = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey)
                .taskCandidateOrAssigned(user.getId()).active().orderByTaskId().desc().list();

        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();

            if (processInstance == null) {
                continue;
            }
            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                continue;
            }
            Leave leave = leaveMapper.selectById(new Long(businessKey));
            leave.setTask(BeanUtil.beanToMap(task));
            leave.setProcessInstance(BeanUtil.beanToMap(processInstance));
            leave.setProcessDefinition(BeanUtil.beanToMap(processDefinition));
            results.add(leave);
        }
        return results;
    }

}
