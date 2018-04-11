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
 * 附件表
 * </p>
 *
 * @author monkey
 * @since 2017-11-08
 */
public class ProAttachment extends Model<ProAttachment> {

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
     * 附件名称
     */
	@TableField("attachName")
	private String attachName;
    /**
     * 路径
     */
	private String url;
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
     * 类型：1常规 2重大
     */
	@TableField("folType")
	private Integer folType;
    /**
     * 时间戳
     */
	@TableField("currentTime")
	private String currentTime;

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
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

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
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

	public Integer getFolType() {
		return folType;
	}

	public void setFolType(Integer folType) {
		this.folType = folType;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ProAttachment{" +
			"id=" + id +
			", proId=" + proId +
			", attachName=" + attachName +
			", url=" + url +
			", userId=" + userId +
			", userName=" + userName +
			", createTime=" + createTime +
			", folType=" + folType +
			"}";
	}
}
