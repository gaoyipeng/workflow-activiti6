package com.sxdx.workflow.activiti.rest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Garnett
 * @since 2020-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActIdGroup extends Model<ActIdGroup> {

    private static final long serialVersionUID = 1L;

    @TableId("ID_")
    private String id;

    @TableField("REV_")
    private Integer rev;

    @TableField("NAME_")
    private String name;

    @TableField("TYPE_")
    private String type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
