package com.sxdx.workflow.auth.service;

import com.sxdx.workflow.auth.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author Garnett
 * @since 2020-10-20
 */
public interface IMenuService extends IService<Menu> {
    List<Menu> findUserPermissionsByUserId(long userId);
}
