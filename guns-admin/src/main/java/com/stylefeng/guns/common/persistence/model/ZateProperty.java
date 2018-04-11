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
 * 载体附加属性表
 * </p>
 *
 * @author lgg
 * @since 2018-01-29
 */
public class ZateProperty extends Model<ZateProperty> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 载体id
     */
	@TableField("zateId")
	private Integer zateId;
    /**
     * 1.国有土地;2.农用地;3.建设用地
     */
	private String type;
    /**
     * 创建时间
     */
	@TableField("createTime")
	private Date createTime;
    /**
     * 操作人id
     */
	@TableField("userId")
	private Integer userId;
    /**
     * 操作人
     */
	@TableField("userName")
	private String userName;
    /**
     * 时间戳
     */
	@TableField("currentTime")
	private String currentTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
		return "ZateProperty{" +
			"id=" + id +
			", zateId=" + zateId +
			", type=" + type +
			", createTime=" + createTime +
			", userId=" + userId +
			", userName=" + userName +
			", currentTime=" + currentTime +
			"}";
	}
}
