package com.sxdx.workflow.auth.service.impl;

import com.sxdx.workflow.auth.entity.User;
import com.sxdx.workflow.auth.mapper.RoleMenuMapper;
import com.sxdx.workflow.auth.mapper.UserMapper;
import com.sxdx.workflow.auth.mapper.UserRoleMapper;
import com.sxdx.workflow.auth.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Garnett
 * @since 2020-10-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户
     */
    public User findByName(String username) {
        User user = userMapper.findByName(username);
        return user;
    }

}
