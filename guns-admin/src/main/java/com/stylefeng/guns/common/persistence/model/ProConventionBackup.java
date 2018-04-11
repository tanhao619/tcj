package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author monkey
 * @since 2017-12-21
 */
public class ProConventionBackup extends Model<ProConventionBackup> {

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
     * 情况说明
     */
	@TableField("proConventionInfo")
	private String proConventionInfo;
    /**
     * 下一步工作建议
     */
	@TableField("nextAdvise")
	private String nextAdvise;
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
	@TableField("updateTime")
	private Date updateTime;
    /**
     * 类型:1常规;2重大;
     */
	@TableField("folType")
	private Integer folType;
    /**
     * 是否删除:1是;0否;
     */
	@TableField("isDelete")
	private String isDelete;
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

	public String getProConventionInfo() {
		return proConventionInfo;
	}

	public void setProConventionInfo(String proConventionInfo) {
		this.proConventionInfo = proConventionInfo;
	}

	public String getNextAdvise() {
		return nextAdvise;
	}

	public void setNextAdvise(String nextAdvise) {
		this.nextAdvise = nextAdvise;
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

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
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
		return "ProConventionBackup{" +
			"id=" + id +
			", proId=" + proId +
			", proConventionInfo=" + proConventionInfo +
			", nextAdvise=" + nextAdvise +
			", userId=" + userId +
			", userName=" + userName +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", folType=" + folType +
			", isDelete=" + isDelete +
			", currentTime=" + currentTime +
			", isBack=" + isBack +
			"}";
	}
}
