package com.sxdx.workflow.web.service.impl;

import com.sxdx.workflow.web.entity.Dept;
import com.sxdx.workflow.web.mapper.DeptMapper;
import com.sxdx.workflow.web.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author Garnett
 * @since 2020-10-16
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
