package com.stylefeng.guns.modular.myplatform.dto;

import java.util.Date;

/**
 * Created by sky on 2017/11/24.
 */
public class UpdateListDto {
    private Integer id; //id
    private String name; //项目名称
    private Date createTime; //创建时间
    private String liable; //责任单位
    private String status; //项目阶段
    private String isBigPro; //项目类型
    private Date updateTime; //更新时间


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLiable() {
        return liable;
    }

    public void setLiable(String liable) {
        this.liable = liable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsBigPro() {
        return isBigPro;
    }

    public void setIsBigPro(String isBigPro) {
        this.isBigPro = isBigPro;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
