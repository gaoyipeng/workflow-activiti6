package com.sxdx.workflow.activiti.rest;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxdx.workflow.activiti.rest.entity.Leave;
import com.sxdx.workflow.activiti.rest.mapper.LeaveMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisTest {

    @Autowired
    private LeaveMapper leaveMapper;

    @Test
    public void test(){
        Page<Leave> selectPage = leaveMapper.selectPage(new Page<>(1, 2), new QueryWrapper<Leave>().eq("id", 1));
        System.out.println("----------"+selectPage.toString()+"----"+selectPage.getRecords());
    }
}
