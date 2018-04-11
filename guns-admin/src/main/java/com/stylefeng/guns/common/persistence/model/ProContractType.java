package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 合同类型
 * </p>
 *
 * @author lgg
 * @since 2017-11-16
 */
@TableName("pro_contractType")
public class ProContractType extends Model<ProContractType> {

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

	@TableField("userId")
	private Integer userId;
	@TableField("folType")
	private Integer folType;
	/**
     * 合同类型名
     */
	@TableField("name")
	private String name;

	@TableField("userName")
	private String userName;

	@TableField("currentTime")
	private String currentTime;

	public Integer getFolType() {
		return folType;
	}

	public void setFolType(Integer folType) {
		this.folType = folType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ProContractType{" +
			"id=" + id +
			", name=" + name +
			", proId=" + proId +
			"}";
	}
}
