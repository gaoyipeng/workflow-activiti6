package com.sxdx.workflow.activiti.rest.mapper;

import com.sxdx.workflow.activiti.rest.entity.Leave;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Garnett
 * @since 2020-06-27
 */
@Repository
public interface LeaveMapper extends BaseMapper<Leave> {
    List<Leave> getAll();
}
