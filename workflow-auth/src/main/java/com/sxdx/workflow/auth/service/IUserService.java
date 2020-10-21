package com.sxdx.workflow.auth.service;

import com.sxdx.workflow.auth.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Garnett
 * @since 2020-10-20
 */
public interface IUserService extends IService<User> {

    /**
     * 获取用户
     * @param username
     * @return
     */
    User findByName(String username);
}
