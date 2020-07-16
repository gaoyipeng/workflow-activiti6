package com.sxdx.workflow.activiti.rest.service;

import com.sxdx.common.exception.base.CommonException;
import org.activiti.engine.runtime.ProcessInstance;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface DynamicFromService {
    Map<String, Object> findStartForm(String processDefinitionId) throws Exception;
    Map<String, Object>  getTaskFormByTaskId(String taskId);
}
