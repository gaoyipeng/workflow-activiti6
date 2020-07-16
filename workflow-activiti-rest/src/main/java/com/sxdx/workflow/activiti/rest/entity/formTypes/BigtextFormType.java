package com.sxdx.workflow.activiti.rest.entity.formTypes;

import org.activiti.engine.impl.form.StringFormType;
import org.springframework.stereotype.Component;

/**
 * 大文本、即html的textarea，集成自引擎默认支持的string类型的实现
 */
@Component
public class BigtextFormType extends StringFormType {
    @Override
    public String getName() {
        return "bigtext";
    }
}
