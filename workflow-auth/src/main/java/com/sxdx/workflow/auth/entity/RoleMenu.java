package com.sxdx.workflow.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色菜单关联表
 * </p>
 *
 * @author Garnett
 * @since 2020-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_role_menu")
public class RoleMenu extends Model<RoleMenu> {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
