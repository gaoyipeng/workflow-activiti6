package com.sxdx.workflow.activiti.rest.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sxdx.common.exception.base.CommonException;
import com.sxdx.workflow.activiti.rest.service.DynamicFromService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
public class DynamicFromServiceImpl implements DynamicFromService {

    @Autowired
    private FormService formService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;


    @Override
    public Map<String, Object> findStartForm(String processDefinitionId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        StartFormDataImpl startFormData = (StartFormDataImpl) formService.getStartFormData(processDefinitionId);
        startFormData.setProcessDefinition(null);
        /*
         * 读取enum类型数据，用于下拉框
         */
        List<FormProperty> formProperties = startFormData.getFormProperties();
        for (FormProperty formProperty : formProperties) {
            Map<String, String> values = (Map<String, String>) formProperty.getType().getInformation("values");
            if (values != null) {
                for (Map.Entry<String, String> enumEntry : values.entrySet()) {
                    log.debug("enum, key: {}, value: {}", enumEntry.getKey(), enumEntry.getValue());
                }
                result.put("enum_" + formProperty.getId(), values);
            }
        }
        result.put("form", startFormData);
        return result;
    }


    @Override
    public Map<String, Object> getTaskFormByTaskId(String taskId) {
        Map<String, Object> result = new HashMap<String, Object>();
        TaskFormDataImpl taskFormData = (TaskFormDataImpl) formService.getTaskFormData(taskId);

        // 设置task为null，否则输出json的时候会报错
        taskFormData.setTask(null);

        result.put("taskFormData", taskFormData);
        /*
         * 读取enum类型数据，用于下拉框
         */
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        for (FormProperty formProperty : formProperties) {
            Map<String, String> values = (Map<String, String>) formProperty.getType().getInformation("values");
            if (values != null) {
                for (Map.Entry<String, String> enumEntry : values.entrySet()) {
                    log.debug("enum, key: {}, value: {}", enumEntry.getKey(), enumEntry.getValue());
                }
                result.put(formProperty.getId(), values);
            }
        }
        return result;
    }


}
