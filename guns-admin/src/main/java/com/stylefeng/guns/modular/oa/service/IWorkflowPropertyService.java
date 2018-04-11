package com.stylefeng.guns.modular.oa.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.WorkflowProperty;
import com.stylefeng.guns.modular.oa.dto.QJTimePropDTO;

import java.util.List;

/**
 * OA管理Service
 *
 * @author lgg
 * @Date 2017-12-04 20:07:17
 */
public interface IWorkflowPropertyService extends IService<WorkflowProperty>{

    List<WorkflowProperty> selectProByNameAndWfwId(String name, Integer workflowId);
    /**
     * 封装请假时间
     */
    QJTimePropDTO escapQJTime(Integer workflowId);
}
