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
 * 
 * </p>
 *
 * @author lgg
 * @since 2017-11-08
 */
public class ZateHistory extends Model<ZateHistory> {

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
     * 变更内容
     */
	private String content;
    /**
     * 载体id
     */
	@TableField("zateId")
	private Integer zateId;
    /**
     * 载体资源类型：1土地资源载体  2楼宇或闲置厂房载体 
     */
	private String type;
    /**
     * 地址
     */
	private String address;
    /**
     * 操作人id
     */
	@TableField("userId")
	private Integer userId;
    /**
     * 操作人姓名
     */
	@TableField("userName")
	private String userName;
    /**
     * 创建时间( 提交时间 )
     */
	@TableField("createTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;


	@TableField("currentTime")
	private String currentTime;

	private Integer pid;

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

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

	public Integer getZateId() {
		return zateId;
	}

	public void setZateId(Integer zateId) {
		this.zateId = zateId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		return "ZateHistory{" +
			"id=" + id +
			", field=" + field +
			", content=" + content +
			", zateId=" + zateId +
			", type=" + type +
			", address=" + address +
			", userId=" + userId +
			", userName=" + userName +
			", createTime=" + createTime +
			"}";
	}
}
