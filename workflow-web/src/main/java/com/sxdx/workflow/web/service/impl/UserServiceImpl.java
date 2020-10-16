package com.sxdx.workflow.web.service.impl;

import com.sxdx.workflow.web.entity.User;
import com.sxdx.workflow.web.mapper.UserMapper;
import com.sxdx.workflow.web.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Garnett
 * @since 2020-10-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
