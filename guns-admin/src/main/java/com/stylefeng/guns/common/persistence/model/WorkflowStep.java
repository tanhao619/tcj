package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * OA审批流程步骤表
 * </p>
 *
 * @author lgg
 * @since 2017-12-04
 */
public class WorkflowStep extends Model<WorkflowStep> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 审批流程id
     */
	@TableField("workflowId")
	private Integer workflowId;
    /**
     * 审批人
     */
	@TableField("userId")
	private Integer userId;
    /**
     * 审批人账号名
     */
	@TableField("userName")
	private String userName;
    /**
     * 审批科室id(预留字段)
     */
	@TableField("deptId")
	private Integer deptId;
    /**
     * 发起角色id
     */
	@TableField("roleId")
	private Integer roleId;
    /**
     * v0.1 (老版本，以新版本为准)审批意见:0.未审批;1.不同意;2.同意; 3.收文无法办结；4.收文办结; （如果不同意  主体流程的状态置为  审批未通过）（如果收文无法办结：立即把主体流程的状态置为  收文无法办结）
	 * v0.2审批意见:0.未审批;1.不同意;2.同意; 3.收文办结; （如果不同意  主体流程的状态置为  审批未通过）
     */
	@TableField("apprAdvice")
	private String apprAdvice;
    /**
     * 审批备注/说明/意见建议
     */
	private String comment;
    /**
     * 审批步骤:1,2,3...
     */
	private String step;
    /**
     * 当前流程步骤是否结束:0进行中(可继续走下个流程）;1已结束(包括审批未通过、办公室备案归档,即不可再往下走);
     */
	@TableField("isEnd")
	private String isEnd;
    /**
     * 审批时间
     */
	@TableField("apprTime")
	private Date apprTime;
    /**
     * 创建时间
     */
	@TableField("createTime")
	private Date createTime;

	/**
	 * 流程每一步的审批人职位
	 * @return
	 */
	private String duty;

	/**
	 * 收文分组标识
	 * @return
	 */
	@TableField("swGpSn")
	private  String swGpSn;

	/**
	 * 分配人
	 * @return
	 */
	@TableField("fenpeiUsers")
	private String fenpeiUsers;

	public String getFenpeiUsers() {
		return fenpeiUsers;
	}

	public void setFenpeiUsers(String fenpeiUsers) {
		this.fenpeiUsers = fenpeiUsers;
	}

	public String getSwGpSn() {
		return swGpSn;
	}

	public void setSwGpSn(String swGpSn) {
		this.swGpSn = swGpSn;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Integer workflowId) {
		this.workflowId = workflowId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getApprAdvice() {
		return apprAdvice;
	}

	public void setApprAdvice(String apprAdvice) {
		this.apprAdvice = apprAdvice;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public Date getApprTime() {
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WorkflowStep{" +
				"id=" + id +
				", workflowId=" + workflowId +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", deptId=" + deptId +
				", roleId=" + roleId +
				", apprAdvice='" + apprAdvice + '\'' +
				", comment='" + comment + '\'' +
				", step='" + step + '\'' +
				", isEnd='" + isEnd + '\'' +
				", apprTime=" + apprTime +
				", createTime=" + createTime +
				", duty='" + duty + '\'' +
				", swGpSn='" + swGpSn + '\'' +
				'}';
	}
}
