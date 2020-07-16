package com.sxdx.workflow.activiti.rest;

import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserAndGroupTest {
    private ProcessEngine processEngine;
    private IdentityService identityService;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;

    /**
     * 获取流程引擎及各个Service
     */
    @Before
    public void before(){
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
                .createStandaloneProcessEngineConfiguration();
        processEngineConfiguration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti-demo?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("gaoyipeng");
        //如果表不存在，则自动创建表
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngine = processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine.toString());

        repositoryService = processEngine.getRepositoryService();
        identityService = processEngine.getIdentityService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();

        assertNotNull(processEngine);
    }

    public void createUserAndGroup(){
        /**
         * 初始化审批人 act_id_user: deptmen， hrmen
         */
        User deptmen = identityService.newUser("deptmen");
        deptmen.setFirstName("部门领导");
        identityService.saveUser(deptmen);

        User hrmen = identityService.newUser("hrmen");
        hrmen.setFirstName("人事领导");
        identityService.saveUser(hrmen);

        assertEquals(2,identityService.createUserQuery().count());

        /**
         * 初始化组 act_id_group: deptLeader, hr
         * 在Activiti中组分为2种：
         *    assignment：普通的岗位角色，是用户分配业务中的功能权限
         *    security-role: 安全角色，全局管理用户组织及整个流程的状态
         */
        Group deptLeader = identityService.newGroup("deptLeader");
        deptLeader.setName("deptLeader");
        //扩展字段
        deptLeader.setType("assignment");
        identityService.saveGroup(deptLeader);

        Group hr = identityService.newGroup("hr");
        hr.setName("hr");
        hr.setType("assignment");
        identityService.saveGroup(hr);

        assertEquals(2,identityService.createGroupQuery().count());
        /**
         * 初始化人员、组的关系
         */
        identityService.createMembership("deptmen","deptLeader");
        identityService.createMembership("hrmen","hr");

    }
}
