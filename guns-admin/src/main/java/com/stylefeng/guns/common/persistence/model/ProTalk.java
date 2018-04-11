package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 洽谈信息表
 * </p>
 *
 * @author monkey
 * @since 2017-11-08
 */
public class ProTalk extends Model<ProTalk> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 项目id
     */
	@TableField("proId")
	private Integer proId;
    /**
     * 进展情况
     */
	private String progress;
    /**
     * 存在问题
     */
	private String question;
    /**
     * 下一步举措
     */
	@TableField("nextStep")
	private String nextStep;
    /**
     * 是否需要拜访
     */
	@TableField("isVisit")
	private String isVisit;
    /**
     * 是否需要拜访领导层级
     */
	@TableField("visitLv")
	private String visitLv;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
	/**
	 * 修改时间
	 *
	 */
	@TableField("updateTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
    /**
     * 类型：1常规 2重大
     */
	@TableField("folType")
	private Integer folType;
	/**
	 * 是否删除：1是 0否
	 *
	 */
	@TableField("isDelete")
	private String isDelete;
	/**
	 * 时间戳
	 */
	@TableField("currentTime")
	private String currentTime;

	/**
	 * 是否修改（新增）
	 */
	@TableField("isBack")
	private String isBack;

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

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

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public String getIsVisit() {
		return isVisit;
	}

	public void setIsVisit(String isVisit) {
		this.isVisit = isVisit;
	}

	public String getVisitLv() {
		return visitLv;
	}

	public void setVisitLv(String visitLv) {
		this.visitLv = visitLv;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ProTalk{" +
			"id=" + id +
			", proId=" + proId +
			", progress=" + progress +
			", question=" + question +
			", nextStep=" + nextStep +
			", isVisit=" + isVisit +
			", visitLv=" + visitLv +
			", userId=" + userId +
			", userName=" + userName +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", folType=" + folType +
				", isDelete=" + isDelete +
				", currentTime=" + currentTime +
				", isBack='" + isBack +
			"}";
	}
}
