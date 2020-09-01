package com.sxdx.workflow.activiti.rest.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxdx.workflow.activiti.rest.entity.ActIdUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Garnett
 * @since 2020-08-31
 */
@Repository
public interface ActIdUserMapper extends BaseMapper<ActIdUser> {
    List<ActIdUser> getAll();
}
