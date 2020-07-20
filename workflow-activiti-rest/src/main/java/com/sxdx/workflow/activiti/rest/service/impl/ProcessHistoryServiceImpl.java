package com.sxdx.workflow.activiti.rest.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxdx.common.config.GlobalConfig;
import com.sxdx.workflow.activiti.rest.service.ProcessHistoryService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessHistoryServiceImpl implements ProcessHistoryService {
    @Autowired
    private HistoryService historyService;

    @Override
    public List<HistoricTaskInstance> getHistoryTaskList(String finished,String processInstanceId) {
        List<HistoricTaskInstance> list = null;
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
        List<HistoricActivityInstance> list = null;
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
        List<HistoricVariableInstance> list = null;
        list = historyService
                .createHistoricVariableInstanceQuery()//创建一个历史的流程变量查询对象
                .processInstanceId(processInstanceId)//
                .list();
        return list;
    }

    @Override
    public PageInfo<HistoricProcessInstance> getFinishedInstanceList(String processDefinitionId,int pageNum, int pageSize) {
        List<HistoricProcessInstance> list = null;

        PageHelper.startPage(pageNum, pageSize);
        list = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionId(processDefinitionId)
                .finished().list();
        PageInfo<HistoricProcessInstance> pageAttachments = new PageInfo<>(list);
        return pageAttachments;
    }
}
