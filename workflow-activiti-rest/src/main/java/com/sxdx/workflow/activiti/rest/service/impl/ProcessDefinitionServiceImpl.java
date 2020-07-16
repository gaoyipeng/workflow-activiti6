package com.sxdx.workflow.activiti.rest.service.impl;

import com.sxdx.common.util.StringUtils;
import com.sxdx.workflow.activiti.rest.service.ProcessDefinitionService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.ZipInputStream;

@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    @Transactional
    public void deployProcessDefinition(String filePath) throws FileNotFoundException
    {
        if (StringUtils.isNotBlank(filePath)) {
            if (filePath.endsWith(".zip") || filePath.endsWith(".bar")) {
                ZipInputStream inputStream = new ZipInputStream(new FileInputStream(filePath));
                repositoryService.createDeployment()
                        .addZipInputStream(inputStream)
                        .deploy();
            } else if (filePath.endsWith(".bpmn")) {
                repositoryService.createDeployment()
                        .addInputStream(filePath, new FileInputStream(filePath))
                        .deploy();
            }
        }
    }
}
