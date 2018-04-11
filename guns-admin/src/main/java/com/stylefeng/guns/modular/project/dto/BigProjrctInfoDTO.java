package com.stylefeng.guns.modular.project.dto;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * 重大保重项目的page list dto
 * @author dengshaojun
 * @date 2017/11/8
 */
public class BigProjrctInfoDTO {
    private String id;
    private String name; //投资项目名称
    private Date createTime; //创建时间
    private String packPosition; //策划包装定位
    private String categary; //具体行业分类
    private String adviseOpeType; //建议实施方式
    private Data updateTime; //修改时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPackPosition() {
        return packPosition;
    }

    public void setPackPosition(String packPosition) {
        this.packPosition = packPosition;
    }

    public String getCategary() {
        return categary;
    }

    public void setCategary(String categary) {
        this.categary = categary;
    }

    public String getAdviseOpeType() {
        return adviseOpeType;
    }

    public void setAdviseOpeType(String adviseOpeType) {
        this.adviseOpeType = adviseOpeType;
    }

    public Data getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Data updateTime) {
        this.updateTime = updateTime;
    }

}
