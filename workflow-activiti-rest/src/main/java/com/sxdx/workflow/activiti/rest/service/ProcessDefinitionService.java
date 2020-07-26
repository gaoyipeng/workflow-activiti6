package com.sxdx.workflow.activiti.rest.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageInfo;
import com.sxdx.workflow.activiti.rest.entity.ProcessDefinitionEntityImplVo;
import org.activiti.engine.repository.ProcessDefinition;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


public interface ProcessDefinitionService {
    void deployProcessDefinition(String filePath) throws FileNotFoundException;
    PageInfo<ProcessDefinitionEntityImplVo> findProcessDefinition(int pageNum, int pageSize, String processDefinitionKey, String processDefinitionName) ;
}
