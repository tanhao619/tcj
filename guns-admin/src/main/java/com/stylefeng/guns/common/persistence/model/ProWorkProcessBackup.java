package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author monkey
 * @since 2017-12-21
 */
@TableName("pro_workProcess_backup")
public class ProWorkProcessBackup extends Model<ProWorkProcessBackup> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	private Integer id;
    /**
     * 项目id
     */
	@TableField("proId")
	private Integer proId;
    /**
     * 工作进度
     */
	@TableField("workProcess")
	private String workProcess;
    /**
     * 操作人id
     */
	@TableField("userId")
	private Integer userId;
    /**
     * 操作人员
     */
	@TableField("userName")
	private String userName;
    /**
     * 创建时间
     */
	@TableField("createTime")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("updateTime")
	private Date updateTime;
    /**
     * 类型:1常规;2重大;
     */
	@TableField("folType")
	private Integer folType;
    /**
     * 时间戳
     */
	@TableField("currentTime")
	private String currentTime;
    /**
     * 是否修改：1.是;0.否;
     */
	@TableField("isBack")
	private String isBack;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public String getWorkProcess() {
		return workProcess;
	}

	public void setWorkProcess(String workProcess) {
		this.workProcess = workProcess;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getFolType() {
		return folType;
	}

	public void setFolType(Integer folType) {
		this.folType = folType;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ProWorkProcessBackup{" +
			"id=" + id +
			", proId=" + proId +
			", workProcess=" + workProcess +
			", userId=" + userId +
			", userName=" + userName +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", folType=" + folType +
			", currentTime=" + currentTime +
			", isBack=" + isBack +
			"}";
	}
}
