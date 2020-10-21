package com.sxdx.workflow.auth.service.impl;

import com.sxdx.workflow.auth.entity.UserRole;
import com.sxdx.workflow.auth.mapper.UserRoleMapper;
import com.sxdx.workflow.auth.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author Garnett
 * @since 2020-10-20
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
