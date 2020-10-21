package com.sxdx.workflow.auth.service.impl;

import com.sxdx.workflow.auth.entity.Menu;
import com.sxdx.workflow.auth.mapper.MenuMapper;
import com.sxdx.workflow.auth.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Garnett
 * @since 2020-10-20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> findUserPermissionsByUserId(long userId) {
        return menuMapper.findUserPermissionsByUserId(userId);
    }
}
