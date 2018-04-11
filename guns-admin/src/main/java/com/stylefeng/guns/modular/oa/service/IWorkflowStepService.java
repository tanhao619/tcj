package com.stylefeng.guns.modular.oa.service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.WorkflowStep;
import com.stylefeng.guns.modular.oa.dto.*;

import java.util.List;
import java.util.Map;

/**
 * OA管理Service
 *
 * @author lgg
 * @Date 2017-12-04 13:06:58
 */
public interface IWorkflowStepService extends IService<WorkflowStep>{
    /**
     * 获取【我审批的】列表&搜索 = 我的账号已审批的流程 + 2、我的角色可审批的流程
     */
    Page<ShenPiWorkflowDTO> shenpiList(Map<String,Object> map);

    /**
     * 获取【我审批的】-未审批数
     */
    Integer  getUnShenpiApprNum();

    /**
     *  【我审批的】-一般审批详情审批流程 ,用于审批未审批的流程 和 查看已审批的流程详情
     *  @return
     */
    NormalDetailFlowsDTO getDetailflows(Integer workflowId,String flowType,String fenleiType);

    /**
     *【我审批的】-收文审批  详情审批流程 ,用于审批未审批的流程 和 查看已审批的流程详情
     */
    SWDetailFlowsDTO getShouWenDetailflows(Integer workflowId, String flowType, String fenleiType);
    /**
     * 审批一般审批流程
     */
   Boolean apprNormalflow(Integer workflowId,String step,String type,String spAdvice);

    /**
     * 审批收文审批流程
     */
    Boolean apprShouWenflow(String userFPDTOsStr,Integer workflowId,String step,String step2Type);
    /**
     * 是否有该流程的审批权限
     */
    Boolean isCanAppr(Integer workflowId);

    /**
     * 通过workflowId 与step与userid确定可分配人
     * @param workflowId
     * @param step
     * @return
     */
    SWFPUsersDTO getFenpeiUsers(Integer workflowId, String step);

    /**
     * 获取办结详情
     */
    List<BJDetailDTO> getBanjieDetail(Integer workflowId);
    /**
     *【办结】-办结  详情审批流程
     */
    SWDetailFlowsDTO getBanjieDetailflows(Integer workflowId, String flowType, String fenleiType);

    Boolean bJSWworkflow(Integer workflowId);
}

