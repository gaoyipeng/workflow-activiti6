package com.sxdx.workflow.activiti.rest.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxdx.common.config.GlobalConfig;
import com.sxdx.common.util.Page;
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
import org.activiti.engine.task.TaskQuery;
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
    public Page getTodoList(String processDefinitionKey, int pageNum,int pageSize) {
        List<Leave> results = new ArrayList<Leave>();

        UserQueryImpl user = new UserQueryImpl();
        user = (UserQueryImpl)identityService.createUserQuery().userId(GlobalConfig.getOperator());

        List<Task> tasks = new ArrayList<Task>();

        Page page = new Page(pageNum,pageSize);
        TaskQuery taskQuery = taskService.createTaskQuery();

        tasks = taskQuery.processDefinitionKey(processDefinitionKey)
                .taskCandidateOrAssigned(user.getId()).active().orderByTaskId().desc()
                .listPage(page.getFirstResult(),page.getMaxResults());

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

        int total = (int) taskQuery.count();
        page.setTotal(total);
        page.setList(results);
        return page;
    }


    public List<Leave>  getAll(){

        /**
         * 如果使用MP帮我们封装好的方法，例如这个 selectBatchIds，则PageHelper分页失效。但是getAll方法是自己写的，则可以继续使用
         * PageHelper.startPage(2,2);
         * List<Integer> list = new ArrayList<>();
         *     list.add(1);
         *     list.add(2);
         *     list.add(3);
         *  List<Leave> leaveList = leaveMapper.selectBatchIds(list);
         */
        PageHelper.startPage(2,2);
        List<Leave> leaveList = leaveMapper.getAll();
        PageInfo<Leave> pageInfo = new PageInfo<>(leaveList);
        return leaveList;
    }
}
