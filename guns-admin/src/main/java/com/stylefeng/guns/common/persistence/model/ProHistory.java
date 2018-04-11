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
 * 通用项目操作记录表
 * </p>
 *
 * @author monkey
 * @since 2017-11-08
 */
public class ProHistory extends Model<ProHistory> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 被修改字段
     */
	private String field;
    /**
     * 操作内容
     */
	private String content;
    /**
     * 项目id
     */
	@TableField("proId")
	private Integer proId;
    /**
     * 跟踪人
     */
	@TableField("folUserId")
	private Integer folUserId;
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
	 * 父级
	 */
	@TableField("pid")
	private Integer pid;

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public Integer getFolUserId() {
		return folUserId;
	}

	public void setFolUserId(Integer folUserId) {
		this.folUserId = folUserId;
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
		return "ProHistory{" +
			"id=" + id +
			", field=" + field +
			", content=" + content +
			", proId=" + proId +
			", folUserId=" + folUserId +
			", userId=" + userId +
			", userName=" + userName +
			", createTime=" + createTime +
			", folType=" + folType +
			", pid=" + pid +
			"}";
	}
}
