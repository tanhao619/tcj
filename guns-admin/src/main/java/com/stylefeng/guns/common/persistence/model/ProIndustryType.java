package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 产业类型
 * </p>
 *
 * @author monkey
 * @since 2018-01-04
 */
@TableName("pro_industryType")
public class ProIndustryType extends Model<ProIndustryType> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 产业类型:1.第一产业;2.第二产业;3.第三产业;
     */
	private String name;
    /**
     * 项目id
     */
	@TableField("proId")
	private Integer proId;
    /**
     * 用户id
     */
	@TableField("userId")
	private Integer userId;
    /**
     * 用户名
     */
	@TableField("userName")
	private String userName;
    /**
     * 修改时间
     */
	@TableField("updateTime")
	private Date updateTime;
    /**
     * 创建时间
     */
	@TableField("createTime")
	private Date createTime;
    /**
     * 时间戳
     */
	@TableField("currentTime")
	private String currentTime;
    /**
     * 类型:1.常规;2.重大;3.载体;
     */
	@TableField("folType")
	private Integer folType;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
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
		return "ProIndustryType{" +
			"id=" + id +
			", name=" + name +
			", proId=" + proId +
			", userId=" + userId +
			", userName=" + userName +
			", updateTime=" + updateTime +
			", createTime=" + createTime +
			", currentTime=" + currentTime +
			", folType=" + folType +
			"}";
	}
}
