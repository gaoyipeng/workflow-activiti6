package com.sxdx.workflow.activiti.rest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.Map;

@Data
public class BaseProcess {
    // 流程任务
    @TableField(exist=false)
    private Map<String, Object> task;
    @TableField(exist=false)
    private Map<String, Object> variables;

    // 运行中的流程实例
    @TableField(exist=false)
    private Map<String, Object> processInstance;

    // 历史的流程实例
    @TableField(exist=false)
    private Map<String, Object> historicProcessInstance;

    // 流程定义
    @TableField(exist=false)
    private Map<String, Object> processDefinition;
}
