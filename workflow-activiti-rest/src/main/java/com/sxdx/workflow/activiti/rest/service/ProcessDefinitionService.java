package com.sxdx.workflow.activiti.rest.service;



import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


public interface ProcessDefinitionService {
    void deployProcessDefinition(String filePath) throws FileNotFoundException;
    List<Map<String ,Object>> findProcessDefinition(int pageNum, int pageSize, String processDefinitionKey, String processDefinitionName) ;
}
