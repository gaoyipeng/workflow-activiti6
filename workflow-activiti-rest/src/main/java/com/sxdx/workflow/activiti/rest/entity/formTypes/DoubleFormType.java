package com.sxdx.workflow.activiti.rest.entity.formTypes;

import cn.hutool.core.util.ObjectUtil;
import org.activiti.engine.form.AbstractFormType;
import org.springframework.stereotype.Component;

@Component
public class DoubleFormType extends AbstractFormType {
    @Override
    public Object convertFormValueToModelValue(String s) {
        return new Double(s);
    }

    @Override
    public String convertModelValueToFormValue(Object o) {
        return ObjectUtil.toString(o);
    }

    @Override
    public String getName() {
        return "double";
    }
}
