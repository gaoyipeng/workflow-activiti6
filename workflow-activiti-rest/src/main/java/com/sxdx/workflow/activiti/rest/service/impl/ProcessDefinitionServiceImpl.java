package com.sxdx.workflow.activiti.rest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.sxdx.common.util.StringUtils;
import com.sxdx.workflow.activiti.rest.service.ProcessDefinitionService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
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
    public List<Map<String ,Object>> findProcessDefinition(int pageNum, int pageSize, String processDefinitionKey, String processDefinitionName)  {

        List<Map<String ,Object>> allList = new ArrayList<>();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();//创建一个流程定义查询
        List<ProcessDefinition> processDefinitionList = null;

        if (processDefinitionKey != null) processDefinitionQuery = processDefinitionQuery.processDefinitionKey(processDefinitionKey);//根据流程定义Key查询
        if (processDefinitionName != null) processDefinitionQuery = processDefinitionQuery.processDefinitionNameLike(processDefinitionName);//根据流程定义name查询

        //PageHelper.startPage(pageNum, pageSize);
        processDefinitionList = processDefinitionQuery.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列
                .orderByProcessDefinitionVersion().desc()//按照版本的降序排列
                .list();

        for (ProcessDefinition processDefinition : processDefinitionList) {
            Map<String ,Object> map = new HashMap<>();
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            map.put("processDefinition", BeanUtil.beanToMap(processDefinition));
            map.put("deployment",BeanUtil.beanToMap(deployment));
            allList.add(map);
        }

        // ============= 对上面查询结果进行封装 否则ProcessDefinition对象无法转为JSON格式数据返回前端 ==================
       /* for (ProcessDefinition processDefinition : list) {
            ProcessDefinitionEntityImplVo processDefinitionEntityImplVo = new ProcessDefinitionEntityImplVo();
            BeanUtils.copyProperties(processDefinition, processDefinitionEntityImplVo);
            list1.add(processDefinitionEntityImplVo);
        }*/
        return allList;
    }
}
