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
 * 土地点位图
 * </p>
 *
 * @author lgg
 * @since 2017-11-08
 */
public class ZateLandimg extends Model<ZateLandimg> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 土地资源载体id
     */
	@TableField("landId")
	private Integer landId;
    /**
     * 土地点位图名字
     */
	private String name;
    /**
     * 土地点位图 - 图片路径
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

	public Integer getLandId() {
		return landId;
	}

	public void setLandId(Integer landId) {
		this.landId = landId;
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
		return "ZateLandimg{" +
			"id=" + id +
			", landId=" + landId +
			", name=" + name +
			", url=" + url +
			", userId=" + userId +
			", userName=" + userName +
			", createTime=" + createTime +
			"}";
	}
}
