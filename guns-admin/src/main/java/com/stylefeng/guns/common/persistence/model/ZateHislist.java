package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author lgg
 * @since 2017-11-16
 */
@TableName("zateHislist")
public class ZateHislist extends Model<ZateHislist> {

    private static final long serialVersionUID = 1L;

	@TableField("zateId")
	private Integer zateId;
	private String name;
	private String type;
	private String address;
	private String unit;
	@TableField("fillContacter")
	private String fillContacter;
	@TableField("fillTel")
	private String fillTel;
	@TableField("createTime")
	private Date createTime;
	@TableField("historyId")
	private Integer historyId;
	@TableField("updateTime")
	private Date updateTime;


	public Integer getZateId() {
		return zateId;
	}

	public void setZateId(Integer zateId) {
		this.zateId = zateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.historyId;
	}

	@Override
	public String toString() {
		return "ZateHislist{" +
			"zateId=" + zateId +
			", name=" + name +
			", type=" + type +
			", address=" + address +
			", unit=" + unit +
			", fillContacter=" + fillContacter +
			", fillTel=" + fillTel +
			", createTime=" + createTime +
			", historyId=" + historyId +
			", updateTime=" + updateTime +
			"}";
	}
}
