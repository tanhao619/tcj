package com.stylefeng.guns.modular.oa.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.WorkflowRead;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.oa.service.IWorkflowReadService;

/**
 * 项目管理Service
 *
 * @author lgg
 * @Date 2018-01-04 19:07:28
 */
@Service
public class WorkflowReadServiceImpl extends ServiceImpl<BaseMapper<WorkflowRead>,WorkflowRead> implements IWorkflowReadService {
}
