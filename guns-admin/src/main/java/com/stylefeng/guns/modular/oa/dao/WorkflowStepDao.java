package com.stylefeng.guns.modular.oa.dao;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.WorkflowStep;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.modular.oa.dto.BJDetailDTO;
import com.stylefeng.guns.modular.oa.dto.NormalFlowDTO;
import com.stylefeng.guns.modular.oa.dto.SWFlowDTO;
import com.stylefeng.guns.modular.oa.dto.ShenPiWorkflowDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * OA管理Dao
 *
 * @author fengshuonan
 * @Date 2017-12-04 13:06:58
 */
@Component
public interface WorkflowStepDao {
    /**
     * 【我审批的】列表&搜索
     * @return
     */
    List<ShenPiWorkflowDTO> shenpiList(@Param("dataScope") DataScope dataScope, @Param("page") Page<ShenPiWorkflowDTO> page, @Param("params") Map<String, Object> map);
    /**
     * 获取【我审批的】-未审批数
     */
    Integer getUnShenpiApprNum(@Param("params")Map<String, Object> map);

    /**
     * 通过workflowId 获取一般审批的审批流程详情
     * @param workflowId
     * @return
     */
    List<NormalFlowDTO> selectNormalFlowByWfwId(Integer workflowId);


    /**
     * 一般审批  切入权限！
     * 通过workflowId 与是否有审批权限isCanAppr 获取审批流程详情
     * @param map
     * @return
     */
    List<NormalFlowDTO> selectAuthNormalFlowByWfwId(@Param("params")Map<String, Object> map);

    /**
     * 收文审批   切入权限！
     * 通过workflowId 与是否有审批权限isCanAppr 获取一般审批的审批流程详情
     * @param map
     * @return
     */
    List<SWFlowDTO> selectAuthShowWenFlowByWfwId(@Param("params")Map<String, Object> map);

    /**
     * 通过 workflowId 和 step确定被审批的流程步骤
     */
    List<WorkflowStep> selectStepByWfwIdStep(@Param("params")Map<String, Object> map);
    /**
     * 通过 workflowId 和 userId确定用户在某一收文审批中的流程唯一那一步步骤
     */
    List<WorkflowStep> selectWfwStepByWfwIdAUid(@Param("params")Map<String, Object> map);
    /**
     * 根据流程查询最大的step
     */
    String selectMaxStepByWfwId(Integer workflowId);

    /**
     * 根据workflowId与确定发起的撤销状态，来确定撤销按钮
     * @param workflowId
     * @return
     */
    String selectRevokeStatusByWfwId(Integer workflowId);
    /**
     *获取一个收文流程中所有已分配的userId
     */
    List<Integer> selectOneSWExsitUserIds(Integer workflowId);

    /**
     * 通过 workflowId 和 step确定收文审批的下一步未被审批的审批流程，未被审批的流程需要做保留
     */
    List<WorkflowStep> selectSWswGpSnNullOrFUYIStepByWfwIdStep(@Param("params")Map<String, Object> map);

    /**
     * 获取办结详情
     */
    List<BJDetailDTO> getBanjieDetail(@Param("params")Map<String, Object> map);


    /**
     * 办结流程详情
     * @param map
     * @return
     */
    List<SWFlowDTO> selectAuthBanjieFlowByWfwId(@Param("params")Map<String, Object> map);

    /**
     * 通过k科员id跟workflowId确定指派科长id
     * @param map
     * @return
     */
    Integer selectBanjieKZByKYidAWfwId(@Param("params")Map<String, Object> map);

    Integer selectHaveAnyShouWenWeiBJ(Integer workflowId);
}
