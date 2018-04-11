package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lgg
 * @since 2018-01-04
 */
public class WorkflowRead extends Model<WorkflowRead> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户id
     */
	@TableField("userId")
	private Integer userId;
	/**
	 * 用户账号
	 */
	@TableField("userAccount")
	private String userAccount;
    /**
     * 流程id
     */
	@TableField("workflowId")
	private Integer workflowId;
    /**
     * 阅读状态：0未读，1已读
     */
	@TableField("readStatus")
	private String readStatus;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Integer workflowId) {
		this.workflowId = workflowId;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WorkflowRead{" +
			"id=" + id +
			", userId=" + userId +
			", workflowId=" + workflowId +
			", readStatus=" + readStatus +
			"}";
	}
}
