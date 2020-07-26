package com.sxdx.workflow.activiti.rest.entity;

import lombok.Data;
import org.activiti.engine.impl.bpmn.data.IOSpecification;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.AbstractEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.apache.xpath.operations.Bool;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: workflow-activiti
 * @description:
 * @author: garnett
 * @create: 2020-07-26 16:01
 **/
@Data
public class ProcessDefinitionEntityImplVo {
    private String id;
    private String name;
    private String description;
    private String key;
    private int vsrsion;
    private String category;
    private String  deploymentId;
    private String resourceName;
    private String  diagramResourceName;
    private int revision;
    private Boolean isInserted;
    private Boolean isUpdated;
    private Boolean isDeleted;

    protected String tenantId = "";
    protected boolean isGraphicalNotationDefined;
    protected Map<String, Object> variables;
    protected boolean hasStartFormKey;
    protected int suspensionState;
    protected boolean isIdentityLinksInitialized;
    protected List<IdentityLinkEntity> definitionIdentityLinkEntities;
    protected IOSpecification ioSpecification;
    protected String engineVersion;
}
