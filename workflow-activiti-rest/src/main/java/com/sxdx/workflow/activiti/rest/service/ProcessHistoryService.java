package com.sxdx.workflow.activiti.rest.service;

import com.github.pagehelper.PageInfo;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;

import java.util.List;

public interface ProcessHistoryService {
    List<HistoricTaskInstance> getHistoryTaskList(String finished,String processInstanceId);
    List<HistoricActivityInstance> getHistoryActInstanceList(String finished, String processInstanceId);
    List<HistoricVariableInstance> getHistoryProcessVariables(String processInstanceId);
    PageInfo<HistoricProcessInstance> getFinishedInstanceList(String processInstanceId, int pageNum, int pageSize);
    PageInfo<HistoricProcessInstance> queryHistoricInstance(int pageNum, int pageSize);



}
