package com.stylefeng.guns.modular.oa.dto;

import java.util.List;

public class SWDetailFlowsDTO {
    /**
     * 流程workflowId
     */
    private Integer workflowId;

    /**
     * 专用于发起的 撤销按钮，撤销状态，如果是发起的审批详情，显示是否能撤销 0可撤销，1 不可撤销，2 已撤销
     */
    private String revokeStatus;


    /**
     * 流程类型,只用于 【审批】待审批按钮控制，1,3,4,5,6 显示 不同意btn/同意btn， 7 只显示同意  8收文不在这里处理
     */
    private String type;
    /**
     * 流程主体list
     */
    private List<SWFlowDTO> SWFlowDTOs;

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

    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
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

    public List<SWFlowDTO> getSWFlowDTOs() {
        return SWFlowDTOs;
    }

    public void setSWFlowDTOs(List<SWFlowDTO> SWFlowDTOs) {
        this.SWFlowDTOs = SWFlowDTOs;
    }
}
