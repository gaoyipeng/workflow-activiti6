package com.sxdx.workflow.activiti.rest.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxdx.workflow.activiti.rest.entity.ActIdGroup;
import com.sxdx.workflow.activiti.rest.entity.ActIdUser;
import com.sxdx.workflow.activiti.rest.mapper.ActIdGroupMapper;
import com.sxdx.workflow.activiti.rest.mapper.ActIdUserMapper;
import com.sxdx.workflow.activiti.rest.service.IActIdGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Garnett
 * @since 2020-08-31
 */
@Service
@Slf4j
public class ActIdGroupServiceImpl extends ServiceImpl<ActIdGroupMapper, ActIdGroup> implements IActIdGroupService {
    @Autowired
    private  ActIdGroupMapper actIdGroupMapper;

    @Override
    public PageInfo getAllGroup(Integer pageNum, Integer pageSize) {
        //在mapper方法前通过指定page页码与size每页条数
        Page<Object> pageObject = PageHelper.startPage(pageNum, pageSize);
        List<ActIdGroup> groupList = actIdGroupMapper.getAll();
        PageInfo<ActIdGroup> pageInfo = new PageInfo<ActIdGroup>(groupList);
        return pageInfo;
    }
}
