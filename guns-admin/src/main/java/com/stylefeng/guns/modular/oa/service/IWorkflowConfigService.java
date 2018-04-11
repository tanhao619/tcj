package com.stylefeng.guns.modular.oa.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.WorkflowConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目管理Service
 *
 * @author lgg
 * @Date 2017-12-17 23:40:08
 */
public interface IWorkflowConfigService extends IService<WorkflowConfig>{
    List<WorkflowConfig> selectWconfigByTD(String type,String duty);
}
