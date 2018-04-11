package com.stylefeng.guns.modular.oa.service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.Workflow;
import com.stylefeng.guns.modular.oa.dto.*;

import java.util.List;
import java.util.Map;

/**
 * OA管理Service
 *
 * @author lgg
 * @Date 2017-12-04 12:59:20
 */
public interface IWorkflowService extends IService<Workflow>{
    Page<WorkflowDTO> list(Map<String, Object> map);

    /**
     * 办结列表
     * @param map
     * @return
     */
    Page<BanjieWorkflowDTO> banjieList(Map<String, Object> map);

    /**
     * 【我发起的】-发起审批-审批人
     */
    List<FaqiRoleDTO> selectApprRole(String type);

    /**
     * 发起审批,返回id
     */
     Boolean insertWorkflow(Map<Object, Object> map);

    /**
     * 审批记录
     */
    List<WorkflowStepDTO> getApprRecord(Integer workflowId);

    /**
     * 办公室主任的归档list
     * @param map
     * @return
     */
    Page<GuidangWorkflowDTO> bgszrGuidangList(Map<String, Object> map);
    /**
     * swgd的归档list
     * @param map
     * @return
     */
    Page<GuidangWorkflowDTO> swgdGuidangList(Map<String, Object> map);

}
