package com.sxdx.workflow.activiti.rest.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * 请假会签任务监听器，当会签任务完成时统计同意的数量
 */
@Component
public class LeaveCounterSignCompleteListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        //approved为审批意见字段
        Boolean approved = Boolean.parseBoolean(delegateTask.getVariable("approved").toString()) ;
        if (approved) {
            Long agreeCounter = (Long) delegateTask.getVariable("approvedCounter");
            delegateTask.setVariable("approvedCounter", agreeCounter + 1);
        }
    }
}
