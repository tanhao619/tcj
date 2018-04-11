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
 * 楼宇或闲置厂房资源载体
 * </p>
 *
 * @author lgg
 * @since 2017-11-08
 */
public class ZateBuilding extends Model<ZateBuilding> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 载体资源名称
     */
	private String name;
    /**
     * 资源具体地址
     */
	private String address;
    /**
     * 建筑面积 (平方米)
     */
	private Double area;
    /**
     * 产权性质：1出租  2出售  3 租售一体
     */
	private String property;
    /**
     * 入驻产业类别: 1第一产业， 2第二产业 ，3第三产业
     */
	@TableField("induType")
	private String induType;
    /**
     * 权属
     */
	private String owne;
    /**
     * 权属联系人
     */
	@TableField("owneContacter")
	private String owneContacter;
    /**
     * 权属联系人电话
     */
	@TableField("owneTel")
	private String owneTel;
    /**
     * 入驻条件
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fillTime;
    /**
     * 填报联系人姓名
     */
	@TableField("fillContacter")
	private String fillContacter;
    /**
     * 填报联系人电话
     */
	@TableField("fillTel")
	private String fillTel;
	private String remark;
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
     * 载体状态：1未使用 2已使用  3已停用
     */
	private String status;

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

	@TableField("currentTime")
	private String currentTime;

	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
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

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getInduType() {
		return induType;
	}

	public void setInduType(String induType) {
		this.induType = induType;
	}

	public String getOwne() {
		return owne;
	}

	public void setOwne(String owne) {
		this.owne = owne;
	}

	public String getOwneContacter() {
		return owneContacter;
	}

	public void setOwneContacter(String owneContacter) {
		this.owneContacter = owneContacter;
	}

	public String getOwneTel() {
		return owneTel;
	}

	public void setOwneTel(String owneTel) {
		this.owneTel = owneTel;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ZateBuilding{" +
			"id=" + id +
			", name=" + name +
			", address=" + address +
			", area=" + area +
			", property=" + property +
			", induType=" + induType +
			", owne=" + owne +
			", owneContacter=" + owneContacter +
			", owneTel=" + owneTel +
			", condition=" + condition +
			", unit=" + unit +
			", fillTime=" + fillTime +
			", fillContacter=" + fillContacter +
			", fillTel=" + fillTel +
			", remark=" + remark +
			", createUserId=" + createUserId +
			", updateUserId=" + updateUserId +
			", status=" + status +
			"}";
	}
}
