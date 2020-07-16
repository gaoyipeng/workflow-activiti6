package com.sxdx.workflow.activiti.rest.entity.formTypes;

import org.activiti.engine.form.AbstractFormType;
import org.springframework.stereotype.Component;

@Component
public class JavascriptFormType extends AbstractFormType {
    /**
     * 把表单中的值转换为实际的对象
     * @param propertyValue
     * @return
     */
    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        return propertyValue;
    }

    /**
     * 把实际对象的值转换为表单中的值
     * @param modelValue
     * @return
     */
    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        return (String) modelValue;
    }

    /**
     * 定义表单类型的标识符
     *
     * @return
     */
    @Override
    public String getName() {
        return "javascript";
    }
}
