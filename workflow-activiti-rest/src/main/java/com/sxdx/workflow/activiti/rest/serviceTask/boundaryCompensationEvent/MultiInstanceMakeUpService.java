package com.sxdx.workflow.activiti.rest.serviceTask.boundaryCompensationEvent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class MultiInstanceMakeUpService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("多实例补偿操作~~~"+delegateExecution.getEventName());
    }
}
