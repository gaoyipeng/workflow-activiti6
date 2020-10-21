package com.sxdx.workflow.auth.mapper;

import com.sxdx.workflow.auth.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author Garnett
 * @since 2020-10-20
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findUserPermissionsByUserId(long userId );

}
