package com.sxdx.workflow.activiti.rest.service;


import java.io.FileNotFoundException;


public interface ProcessDefinitionService {
    void deployProcessDefinition(String filePath) throws FileNotFoundException;
}
