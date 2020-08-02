package com.sxdx.workflow.activiti.rest.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxdx.common.config.GlobalConfig;
import com.sxdx.common.util.Page;
import com.sxdx.workflow.activiti.rest.service.ProcessHistoryService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessHistoryServiceImpl implements ProcessHistoryService {
    @Autowired
    private HistoryService historyService;

    @Override
    public List<HistoricTaskInstance> getHistoryTaskList(String finished,String processInstanceId) {
        List<HistoricTaskInstance> list = new ArrayList<>();
        if (finished.equals("0")){
            list = historyService // 历史任务Service
                    .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                    .orderByHistoricTaskInstanceStartTime().asc() //排序
                    //.taskAssignee(GlobalConfig.getOperator()) // 指定办理人
                    .processInstanceId(processInstanceId)
                    .finished() // 查询已经完成的任务
                    .list();
        }else if (finished.equals("1")){
            list = historyService // 历史任务Service
                    .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                    .orderByHistoricTaskInstanceStartTime().asc()
                    //.taskAssignee(GlobalConfig.getOperator()) // 指定办理人
                    .processInstanceId(processInstanceId)
                    .unfinished()// 查询未完成的任务
                    .list();
        }else{
            list = historyService // 历史任务Service
                    .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                    .orderByHistoricTaskInstanceStartTime().asc()
                    //.taskAssignee(GlobalConfig.getOperator()) // 指定办理人
                    .processInstanceId(processInstanceId)
                    .list();
        }

        return list;
    }

    @Override
    public List<HistoricActivityInstance> getHistoryActInstanceList(String finished, String processInstanceId) {
        List<HistoricActivityInstance> list = new ArrayList<>();
        if (finished.equals("0")){
            list = historyService // 历史任务Service
                    .createHistoricActivityInstanceQuery()
                    .orderByHistoricActivityInstanceStartTime().asc()
                    .processInstanceId(processInstanceId)
                    .finished()
                    .list();
        }else if (finished.equals("1")){
            list = historyService // 历史任务Service
                    .createHistoricActivityInstanceQuery()
                    .orderByHistoricActivityInstanceStartTime().asc()
                    .processInstanceId(processInstanceId)
                    .unfinished()
                    .list();
        }else{
            list = historyService // 历史任务Service
                    .createHistoricActivityInstanceQuery()
                    .orderByHistoricActivityInstanceStartTime().asc()
                    .processInstanceId(processInstanceId)
                    .list();
        }

        return list;
    }

    @Override
    public List<HistoricVariableInstance> getHistoryProcessVariables( String processInstanceId) {
        List<HistoricVariableInstance> list = new ArrayList<>();
        list = historyService
                .createHistoricVariableInstanceQuery()//创建一个历史的流程变量查询对象
                .processInstanceId(processInstanceId)//
                .list();
        return list;
    }

    @Override
    public Page getFinishedInstanceList(int pageNum, int pageSize,String processDefinitionId) {
        List<HistoricProcessInstance> list = new ArrayList<>();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        Page page = new Page(pageNum,pageSize);

        if (processDefinitionId != null ) {
            historicProcessInstanceQuery =  historicProcessInstanceQuery.processDefinitionId(processDefinitionId);
        }
        list = historicProcessInstanceQuery
                .orderByProcessInstanceStartTime().asc()//排序
                .finished()
                .listPage(page.getFirstResult(),page.getMaxResults());

        int total = (int) historicProcessInstanceQuery.count();
        page.setTotal(total);
        page.setList(list);
        return page;
    }

    @Override
    public Page queryHistoricInstance(int pageNum, int pageSize,String processDefinitionId) {
        List<HistoricProcessInstance> list = new ArrayList<>();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        Page page = new Page(pageNum,pageSize);
        if (processDefinitionId != null ) {
            historicProcessInstanceQuery =  historicProcessInstanceQuery.processDefinitionId(processDefinitionId);
        }
        list = historicProcessInstanceQuery
                .orderByProcessInstanceStartTime().asc()//排序
                .listPage(page.getFirstResult(),page.getMaxResults());
        int total = (int) historicProcessInstanceQuery.count();
        page.setTotal(total);
        page.setList(list);
        return page;
    }
}
