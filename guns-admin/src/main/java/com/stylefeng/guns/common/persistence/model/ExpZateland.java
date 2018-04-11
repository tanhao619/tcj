package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author lgg
 * @since 2017-11-26
 */
public class ExpZateland extends Model<ExpZateland> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	private Integer id;
    /**
     * 载体资源名称
     */
	private String name;
    /**
     * 资源地点
     */
	private String address;
    /**
     * 资源面积（亩）
     */
	private Double area;
    /**
     * 资源状态:1.未使用;2.已使用;3.已停用;
     */
	private String status;
    /**
     * 用地性质:1.国有土地;2.集体土地;3.农用地;4.建设用地;5.未用地;
     */
	private String property;
    /**
     * 规划产业定位
     */
	@TableField("induPosition")
	private String induPosition;
    /**
     * 地块现状
     */
	private String situation;
    /**
     * 规划设计条件
     */
	private String condition;
    /**
     * 填报单位
     */
	private String unit;
    /**
     * 填报时间
     */
	@TableField("fillTime")
	private Date fillTime;
    /**
     * 填报人姓名
     */
	@TableField("fillContacter")
	private String fillContacter;
    /**
     * 填报人电话
     */
	@TableField("fillTel")
	private String fillTel;
    /**
     * 备注
     */
	private String remark;
    /**
     * 创建时间
     */
	@TableField("createTime")
	private Date createTime;
    /**
     * 创建人
     */
	@TableField("createUserId")
	private Integer createUserId;
    /**
     * 修改人
     */
	@TableField("updateUserId")
	private Integer updateUserId;
    /**
     * 修改时间
     */
	@TableField("updateTime")
	private Date updateTime;
    /**
     * 时间戳
     */
	@TableField("currentTime")
	private String currentTime;
	@TableField("createUser")
	private String createUser;


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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getInduPosition() {
		return induPosition;
	}

	public void setInduPosition(String induPosition) {
		this.induPosition = induPosition;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getFillTime() {
		return fillTime;
	}

	public void setFillTime(Date fillTime) {
		this.fillTime = fillTime;
	}

	public String getFillContacter() {
		return fillContacter;
	}

	public void setFillContacter(String fillContacter) {
		this.fillContacter = fillContacter;
	}

	public String getFillTel() {
		return fillTel;
	}

	public void setFillTel(String fillTel) {
		this.fillTel = fillTel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ExpZateland{" +
			"id=" + id +
			", name=" + name +
			", address=" + address +
			", area=" + area +
			", status=" + status +
			", property=" + property +
			", induPosition=" + induPosition +
			", situation=" + situation +
			", condition=" + condition +
			", unit=" + unit +
			", fillTime=" + fillTime +
			", fillContacter=" + fillContacter +
			", fillTel=" + fillTel +
			", remark=" + remark +
			", createTime=" + createTime +
			", createUserId=" + createUserId +
			", updateUserId=" + updateUserId +
			", updateTime=" + updateTime +
			", currentTime=" + currentTime +
			", createUser=" + createUser +
			"}";
	}
}
