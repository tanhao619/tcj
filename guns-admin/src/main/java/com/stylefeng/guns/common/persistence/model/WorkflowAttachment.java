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
 * OA审批流程附件表
 * </p>
 *
 * @author lgg
 * @since 2017-12-04
 */
public class WorkflowAttachment extends Model<WorkflowAttachment> {

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
     * 审批附件名
     */
	private String name;
    /**
     * 审批附件路径
     */
	private String url;
    /**
     * 发起审批人id
     */
	@TableField("userId")
	private Integer userId;
    /**
     * 发起审批人账号名
     */
	@TableField("userName")
	private String userName;
    /**
     * 创建时间
     */
	@TableField("createTime")
	private Date createTime;


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		return "WorkflowAttachment{" +
			"id=" + id +
			", workflowId=" + workflowId +
			", name=" + name +
			", url=" + url +
			", userId=" + userId +
			", userName=" + userName +
			", createTime=" + createTime +
			"}";
	}
}
