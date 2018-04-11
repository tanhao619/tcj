package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author lgg
 * @since 2017-11-08
 */
public class Zate extends Model<Zate> {

    private static final long serialVersionUID = 1L;

	private Integer zid;
	@TableField("ordId")
	private String ordId;
	private String name;
	private Long type;
	private String typename;
	private String address;
	private String unit;
	private String status;
	@TableField("fillContacter")
	private String fillContacter;
	@TableField("fillTel")
	private String fillTel;
	@TableField("fillTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fillTime;

	@TableField("createTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	@TableField("updateTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
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

	public Integer getZid() {
		return zid;
	}

	public void setZid(Integer zid) {
		this.zid = zid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getFillTime() {
		return fillTime;
	}

	public void setFillTime(Date fillTime) {
		this.fillTime = fillTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.zid;
	}

	@Override
	public String toString() {
		return "Zate{" +
			"zid=" + zid +
			", name=" + name +
			", type=" + type +
			", typename=" + typename +
			", address=" + address +
			", unit=" + unit +
			", status=" + status +
			", fillContacter=" + fillContacter +
			", fillTel=" + fillTel +
			", fillTime=" + fillTime +
			"}";
	}
}
