package com.stylefeng.guns.modular.oa.dto;

import com.stylefeng.guns.common.persistence.model.WorkflowAttachment;

import java.util.Date;
import java.util.List;

public class WorkflowDTO {
    private Integer id;
    /**
     * 审批类型
     */
    private String type;

    /**
     * 发起人角色
     */
    private String role;
    /**
     * 审批附件
     */
    private List<WorkFlowAttachmentDTO> attachments;
    /**
     * 审批状态:1.审批中;2.审批未通过;3.审批通过;4办结中;5已办结(办结成功);6办结失败7.已撤销;
     */
    private String status;
    /**
     * 审批编号:生成策略 "1" += id,不直接使用id
     */
    private String ordnum;
    /**
     * 发起时间
     */
    private Date apprTime;
    /**
     * 收文审批结束时间
     */
    private Date endTime;

    /**
     * 是否显示撤销按钮（是否能撤销）：1显示撤销，0不显示
     * @return
     */
    private String isShowRevo;

    /**
     * 请假审批的 销假按钮：0可销假，1不可销假，2.已销假
     * @return
     */
    private String xiaojBtn;

    public String getXiaojBtn() {
        return xiaojBtn;
    }

    public void setXiaojBtn(String xiaojBtn) {
        this.xiaojBtn = xiaojBtn;
    }

    public String getIsShowRevo() {
        return isShowRevo;
    }

    public void setIsShowRevo(String isShowRevo) {
        this.isShowRevo = isShowRevo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<WorkFlowAttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<WorkFlowAttachmentDTO> attachments) {
        this.attachments = attachments;
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

    public Date getApprTime() {
        return apprTime;
    }

    public void setApprTime(Date apprTime) {
        this.apprTime = apprTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
