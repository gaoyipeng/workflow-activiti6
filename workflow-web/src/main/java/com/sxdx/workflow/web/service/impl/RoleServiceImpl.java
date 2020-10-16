package com.sxdx.workflow.web.service.impl;

import com.sxdx.workflow.web.entity.Role;
import com.sxdx.workflow.web.mapper.RoleMapper;
import com.sxdx.workflow.web.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Garnett
 * @since 2020-10-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
