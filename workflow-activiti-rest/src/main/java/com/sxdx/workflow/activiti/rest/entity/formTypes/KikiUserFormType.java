package com.sxdx.workflow.activiti.rest.entity.formTypes;

import cn.hutool.core.util.ObjectUtil;
import freemarker.template.utility.StringUtil;
import org.activiti.engine.form.AbstractFormType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class KikiUserFormType extends AbstractFormType {
    /**
     * 把表单中的值转换为实际的对象
     * @param s
     * @return
     */
    @Override
    public Object convertFormValueToModelValue(String s) {
        String[] split = StringUtil.split(s, ',');
        return Arrays.asList(split);
    }

    /**
     * 把实际对象的值转换为表单中的值
     * @param o
     * @return
     */
    @Override
    public String convertModelValueToFormValue(Object o) {
        return ObjectUtil.toString(0);
    }

    /**
     * 定义表单类型的标识符
     */
    @Override
    public String getName() {
        return "users";
    }
}
