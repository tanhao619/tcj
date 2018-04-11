package com.stylefeng.guns.modular.project.dto;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/8.
 */
public class NormalProjrctInfoDTO {
    /**
     * 项目id
     */
    private String id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目创建时间
     */
    private Date createTime;
    /**
     * 项目阶段
     */
    private String status;
    /**
     * 项目投资方公司
     */
    private String investor;
    /**
     * 项目修改时间
     */
    private Data updateTime;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Data updateTime) {
        this.updateTime = updateTime;
    }

    public String getInvestor() {
        return investor;
    }

    public void setInvestor(String investor) {
        this.investor = investor;
    }
}
