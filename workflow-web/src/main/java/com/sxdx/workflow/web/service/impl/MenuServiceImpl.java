package com.sxdx.workflow.web.service.impl;

import com.sxdx.workflow.web.entity.Menu;
import com.sxdx.workflow.web.mapper.MenuMapper;
import com.sxdx.workflow.web.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Garnett
 * @since 2020-10-16
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
