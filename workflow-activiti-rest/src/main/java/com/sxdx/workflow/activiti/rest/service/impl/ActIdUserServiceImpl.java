package com.sxdx.workflow.activiti.rest.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxdx.workflow.activiti.rest.entity.ActIdUser;
import com.sxdx.workflow.activiti.rest.entity.Leave;
import com.sxdx.workflow.activiti.rest.mapper.ActIdUserMapper;
import com.sxdx.workflow.activiti.rest.service.IActIdUserService;
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
public class ActIdUserServiceImpl extends ServiceImpl<ActIdUserMapper, ActIdUser> implements IActIdUserService {

    @Autowired
    private ActIdUserMapper ActIdUserMapper;

    @Override
    public PageInfo getAllUser(Integer pageNum, Integer pageSize) {
        //在mapper方法前通过指定page页码与size每页条数
        Page<Object> pageObject = PageHelper.startPage(pageNum, pageSize);
        List<ActIdUser> userList = ActIdUserMapper.getAll();
        PageInfo<ActIdUser> pageInfo = new PageInfo<>(userList);
        return pageInfo;

    }
}
