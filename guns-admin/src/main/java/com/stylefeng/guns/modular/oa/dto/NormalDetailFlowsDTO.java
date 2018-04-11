package com.stylefeng.guns.modular.oa.dto;

import java.util.List;

public class NormalDetailFlowsDTO {
    /**
     * 流程workflowId
     */
    private Integer workflowId;
    /**
     * 是否结束 true 已结束 false 未结束
     */
    private String isEnd;

    /**
     * 如果是请假审批，的审批时间
     */
    private QJTimePropDTO qJTimePropDTO;

    /**
     * 专用于发起的 撤销按钮，撤销状态，如果是发起的审批详情，显示是否能撤销 0可撤销，1 不可撤销，2 已撤销
     */
    private String revokeStatus;

    /**
     * 审批过程中 判断是否有该流程步骤的审批权限
     * true能 false 不能
     */
    private String isCanSpFlow;

    /**
     * 步骤流程线的结束步骤
     * @return
     */
    private String lineStep;

    /**
     * 步骤流程线的类型
     * "不同意"、"同意"、"待审批"
     * @return
     */
    private String lineType;

    public String getIsCanSpFlow() {
        return isCanSpFlow;
    }

    public void setIsCanSpFlow(String isCanSpFlow) {
        this.isCanSpFlow = isCanSpFlow;
    }

    /**
     * 流程类型,只用于 【审批】待审批按钮控制，1,3,4,5,6 显示 不同意btn/同意btn， 7 只显示同意  8收文不在这里处理
     */
    private String type;

    public String getLineStep() {
        return lineStep;
    }

    public void setLineStep(String lineStep) {
        this.lineStep = lineStep;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    /**
     * 流程主体list
     */

    private List<NormalFlowDTO> NormalFlowDTOs;

    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public QJTimePropDTO getqJTimePropDTO() {
        return qJTimePropDTO;
    }

    public void setqJTimePropDTO(QJTimePropDTO qJTimePropDTO) {
        this.qJTimePropDTO = qJTimePropDTO;
    }

    public String getRevokeStatus() {
        return revokeStatus;
    }

    public void setRevokeStatus(String revokeStatus) {
        this.revokeStatus = revokeStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NormalFlowDTO> getNormalFlowDTOs() {
        return NormalFlowDTOs;
    }

    public void setNormalFlowDTOs(List<NormalFlowDTO> normalFlowDTOs) {
        NormalFlowDTOs = normalFlowDTOs;
    }
}
