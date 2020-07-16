package com.sxdx.workflow.activiti.rest.config;

import com.sxdx.workflow.activiti.rest.entity.formTypes.BigtextFormType;
import com.sxdx.workflow.activiti.rest.entity.formTypes.DoubleFormType;
import com.sxdx.workflow.activiti.rest.entity.formTypes.JavascriptFormType;
import com.sxdx.workflow.activiti.rest.entity.formTypes.KikiUserFormType;
import org.activiti.engine.form.AbstractFormType;
import org.activiti.rest.form.UserFormType;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

    @Autowired
    private ICustomProcessDiagramGenerator customProcessDiagramGenerator;
    @Autowired
    private KikiUserFormType kikiUserFormType;
    @Autowired
    private JavascriptFormType javascriptFormType;
    @Autowired
    private BigtextFormType bigtextFormType;
    @Autowired
    private DoubleFormType doubleFormType;

    /**
     * 解決工作流生成图片乱码问题
     *
     * @param processEngineConfiguration
     */
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        int[] result = {};
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setProcessDiagramGenerator(customProcessDiagramGenerator);
    }

    /**
     * 自定义表单字段类型
     * @return
     */
    @Bean
    public BeanPostProcessor activitiConfigurer() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof SpringProcessEngineConfiguration) {
                    List<AbstractFormType> customFormTypes = Arrays.<AbstractFormType>asList(javascriptFormType,kikiUserFormType,bigtextFormType,doubleFormType);
                    ((SpringProcessEngineConfiguration)bean).setCustomFormTypes(customFormTypes);
                }
                return bean;
            }
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };

    }

}
