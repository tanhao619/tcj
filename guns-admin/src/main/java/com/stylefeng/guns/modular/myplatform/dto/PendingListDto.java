package com.stylefeng.guns.modular.myplatform.dto;

import java.util.Date;

/**
 * Created by Administrator on 2017/11/18.
 */
public class PendingListDto {

    private Integer id; //id
    private String name; //项目名称
    private String category; //所属产业
    private String liable; //责任单位
    private String folType; //跟踪状态
    private String projectType; //项目类型
    private Date createTime; //创建时间
    private Integer jump; //跳转id

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLiable() {
        return liable;
    }

    public void setLiable(String liable) {
        this.liable = liable;
    }

    public String getFolType() {
        return folType;
    }

    public void setFolType(String folType) {
        this.folType = folType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getJump() {
        return jump;
    }

    public void setJump(Integer jump) {
        this.jump = jump;
    }
}
