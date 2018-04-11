package com.stylefeng.guns.modular.zate.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.common.persistence.model.ZateLandimg;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ZateLandDto {

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
     * 面积（单位亩）
     */
    private Double area;
    /**
     * 用地性质:1国有 2农用地 3建设用地
     */
    private String property;
    /**
     * 规划产业定位
     */
    private String induPosition;
    /**
     * 地块现状
     */
    private String situation;
    /**
     * 载体状态：1未使用 2已使用  3已停用
     */
    private String status;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fillTime;
    /**
     * 填报联系人姓名
     */
    private String fillContacter;
    /**
     * 填报联系人电话
     */
    private String fillTel;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 创建人
     */
    private Integer createUserId;
    /**
     * 修改人
     */
    private Integer updateUserId;

    /**
     * 土地资源图
     * @return
     */
    private List<ZateImgDto> zateImgs;

    public List<ZateImgDto> getZateImgs() {
        return zateImgs;
    }

    public void setZateImgs(List<ZateImgDto> zateImgs) {
        this.zateImgs = zateImgs;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
