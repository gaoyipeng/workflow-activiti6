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
public class ActIdUser extends Model<ActIdUser> {

    private static final long serialVersionUID = 1L;

    @TableId("ID_")
    private String id;

    @TableField("REV_")
    private Integer rev;

    @TableField("FIRST_")
    private String first;

    @TableField("LAST_")
    private String last;

    @TableField("EMAIL_")
    private String email;

    @TableField("PWD_")
    private String pwd;

    @TableField("PICTURE_ID_")
    private String pictureId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
