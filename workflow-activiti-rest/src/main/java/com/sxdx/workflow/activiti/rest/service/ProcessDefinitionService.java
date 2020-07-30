package com.sxdx.workflow.activiti.rest.service;


import com.sxdx.common.util.Page;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


public interface ProcessDefinitionService {
    void deployProcessDefinition(String filePath) throws FileNotFoundException;
    Page findProcessDefinition(int pageNum, int pageSize, String processDefinitionKey, String processDefinitionName) ;
}
