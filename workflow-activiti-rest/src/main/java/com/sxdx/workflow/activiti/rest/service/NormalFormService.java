package com.sxdx.workflow.activiti.rest.service;


import io.swagger.annotations.ApiParam;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface NormalFormService  {
    ProcessInstance startWorkflow(String processDefinitionKey, String businessKey, Map<String, Object> variables);
}
