package com.sxdx.workflow.activiti.rest.entity.vo;

import lombok.Data;

@Data
public class AcExecutionEntityImpl  {
    private String id;
    private String parentId;
    private String processInstanceId;
    private String rootProcessInstanceId;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String activitiId;
    private String activitiName;

}
