package com.sxdx.workflow.activiti.rest.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.sxdx.workflow.activiti.rest.entity.ActIdUser;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Garnett
 * @since 2020-08-31
 */
public interface IActIdUserService extends IService<ActIdUser> {
    PageInfo getAllUser(Integer pageNum, Integer pageSize);
}
