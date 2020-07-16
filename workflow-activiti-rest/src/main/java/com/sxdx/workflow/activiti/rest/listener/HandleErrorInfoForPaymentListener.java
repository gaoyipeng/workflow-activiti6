package com.sxdx.workflow.activiti.rest.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class HandleErrorInfoForPaymentListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        String activitiId = delegateExecution.getCurrentActivityId();
        if ("flow-treasurerAudit".equals(activitiId)){
            delegateExecution.setVariable("message","财务审批不通过");
        }else if ("flow-generalManagerAudit".equals(activitiId)){
            delegateExecution.setVariable("message","总经理审批不通过");
        }
    }
}
