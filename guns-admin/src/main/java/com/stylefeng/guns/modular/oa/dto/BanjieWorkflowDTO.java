package com.stylefeng.guns.modular.oa.dto;

import java.util.Date;
import java.util.List;

public class BanjieWorkflowDTO {
    private Integer workflowId;
    /**
     * 审批类型
     */
    private String type;

    /**
     * 审批编号:生成策略 "1" += id,不直接使用id
     */
    private String ordnum;
    /**
     * 收文编号
     */
    private String ordnumSW;
    /**
     * 发起时间
     */
    private String faqiTime;
    /**
     * 指派科室
     */
    private String assignDept;
    /**
     * 指派科长
     */
    private String assignKZ;
    /**
     * 办结附件
     */
    private List<WorkFlowAttachmentDTO> attachments;
    /**
     * 指派时间
     */
    private String assignTime;

    /**
     * 是否办结：  是  否
     */
    private String isBanjie;
    /**
     * 办结时间
     */
    private String banjieTime;

    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrdnum() {
        return ordnum;
    }

    public void setOrdnum(String ordnum) {
        this.ordnum = ordnum;
    }

    public String getOrdnumSW() {
        return ordnumSW;
    }

    public void setOrdnumSW(String ordnumSW) {
        this.ordnumSW = ordnumSW;
    }

    public String getFaqiTime() {
        return faqiTime;
    }

    public void setFaqiTime(String faqiTime) {
        this.faqiTime = faqiTime;
    }

    public String getAssignDept() {
        return assignDept;
    }

    public void setAssignDept(String assignDept) {
        this.assignDept = assignDept;
    }

    public String getAssignKZ() {
        return assignKZ;
    }

    public void setAssignKZ(String assignKZ) {
        this.assignKZ = assignKZ;
    }

    public List<WorkFlowAttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<WorkFlowAttachmentDTO> attachments) {
        this.attachments = attachments;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public String getIsBanjie() {
        return isBanjie;
    }

    public void setIsBanjie(String isBanjie) {
        this.isBanjie = isBanjie;
    }

    public String getBanjieTime() {
        return banjieTime;
    }

    public void setBanjieTime(String banjieTime) {
        this.banjieTime = banjieTime;
    }
}
