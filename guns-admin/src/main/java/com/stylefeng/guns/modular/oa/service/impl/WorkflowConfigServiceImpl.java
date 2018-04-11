package com.stylefeng.guns.modular.oa.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.WorkflowConfig;
import com.stylefeng.guns.modular.oa.dao.WorkflowConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.oa.service.IWorkflowConfigService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目管理Service
 *
 * @author lgg
 * @Date 2017-12-17 23:40:08
 */
@Service
public class WorkflowConfigServiceImpl extends ServiceImpl<BaseMapper<WorkflowConfig>,WorkflowConfig> implements IWorkflowConfigService {
    @Autowired
    private WorkflowConfigDao workflowConfigDao;
    @Override
    public List<WorkflowConfig> selectWconfigByTD(String type,String duty) {
        Map<String, Object> parmsMap = new HashMap<>();
        parmsMap.put("type",type);
        parmsMap.put("duty",duty);
        return workflowConfigDao.selectWconfigByTD(parmsMap);
    }
}
