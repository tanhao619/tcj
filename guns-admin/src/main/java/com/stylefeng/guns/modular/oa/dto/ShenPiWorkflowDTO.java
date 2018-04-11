package com.stylefeng.guns.modular.oa.dto;

import java.util.Date;
import java.util.List;

public class ShenPiWorkflowDTO {

    /**
     *  主流程id
     */
    private Integer workflowId;
    /**
     * 审批类型
     */
    private String type;

    /**
     * 审批状态:1.审批中;2.审批未通过;3.审批通过;4办结中;5已办结(办结成功);6办结失败7.已撤销;
     */
    private String status;

    /**
     * 审批编号
     */
    private String ordnum;

    /**
     * 发起人
     */
    private String apprUserName;

    /**
     *发起人角色
     */
    private String apprRoleName;
    /**
     * 审批附件
     */
    private List<WorkFlowAttachmentDTO> attachments;

    /**
     * 发起时间
     */
    private Date faqiTime;

    /**
     * 收文编号
     */
    private String ordnumSW;
    /**
     * step步骤表id
     */
    private Integer workflowStepId;
    /**
     * 是否审批:0 未审批，1已审批
     */
    private String isAppr;

    /**
     * 审批时间
     */
    private Date apprTime;
    /**
     *审批意见:0.未审批;1.不同意;2.同意; 3.收文办结; （如果不同意  主体流程的状态置为  审批未通过）
     */
    private String apprAdvice;

    public String getOrdnumSW() {
        return ordnumSW;
    }

    public void setOrdnumSW(String ordnumSW) {
        this.ordnumSW = ordnumSW;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdnum() {
        return ordnum;
    }

    public void setOrdnum(String ordnum) {
        this.ordnum = ordnum;
    }

    public String getApprUserName() {
        return apprUserName;
    }

    public void setApprUserName(String apprUserName) {
        this.apprUserName = apprUserName;
    }

    public String getApprRoleName() {
        return apprRoleName;
    }

    public void setApprRoleName(String apprRoleName) {
        this.apprRoleName = apprRoleName;
    }

    public List<WorkFlowAttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<WorkFlowAttachmentDTO> attachments) {
        this.attachments = attachments;
    }

    public Date getFaqiTime() {
        return faqiTime;
    }

    public void setFaqiTime(Date faqiTime) {
        this.faqiTime = faqiTime;
    }

    public Integer getWorkflowStepId() {
        return workflowStepId;
    }

    public void setWorkflowStepId(Integer workflowStepId) {
        this.workflowStepId = workflowStepId;
    }

    public String getIsAppr() {
        return isAppr;
    }

    public void setIsAppr(String isAppr) {
        this.isAppr = isAppr;
    }

    public Date getApprTime() {
        return apprTime;
    }

    public void setApprTime(Date apprTime) {
        this.apprTime = apprTime;
    }

    public String getApprAdvice() {
        return apprAdvice;
    }

    public void setApprAdvice(String apprAdvice) {
        this.apprAdvice = apprAdvice;
    }
}
