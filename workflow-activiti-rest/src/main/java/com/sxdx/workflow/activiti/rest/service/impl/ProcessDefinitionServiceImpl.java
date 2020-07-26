package com.sxdx.workflow.activiti.rest.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxdx.common.util.StringUtils;
import com.sxdx.workflow.activiti.rest.entity.ProcessDefinitionEntityImplVo;
import com.sxdx.workflow.activiti.rest.service.ProcessDefinitionService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public PageInfo<ProcessDefinitionEntityImplVo> findProcessDefinition(int pageNum, int pageSize, String processDefinitionKey, String processDefinitionName)  {

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();//创建一个流程定义查询
        List<ProcessDefinition> list = null;
        List<ProcessDefinitionEntityImplVo> list1 = new ArrayList<>();

        if (processDefinitionKey != null) processDefinitionQuery = processDefinitionQuery.processDefinitionKey(processDefinitionKey);//根据流程定义Key查询
        if (processDefinitionName != null) processDefinitionQuery = processDefinitionQuery.processDefinitionNameLike(processDefinitionName);//根据流程定义name查询

        PageHelper.startPage(pageNum, pageSize);
        list = processDefinitionQuery.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
                .list();//返回一个集合列表，封装流程定义
        PageInfo<ProcessDefinition> pageAttachments1 = new PageInfo<>(list);

        // ============= 对上面查询结果进行封装 ==================
        for (ProcessDefinition processDefinition : list) {
            ProcessDefinitionEntityImplVo processDefinitionEntityImplVo = new ProcessDefinitionEntityImplVo();
            BeanUtils.copyProperties(processDefinition, processDefinitionEntityImplVo);
            list1.add(processDefinitionEntityImplVo);
        }
        PageInfo<ProcessDefinitionEntityImplVo> pageAttachments = new PageInfo<>(list1);
        return pageAttachments;
    }
}
