package com.stylefeng.guns.modular.project.dto;

import java.util.Date;

/**
 * Created by Tanhao on 2017/11/17.
 */
public class NormalProConventionInfoDTO {
    private Integer id;
    //项目牵头单位
    private String proConcatCom;
    //注册公司名称
    private String regComName;
    //注册公司时间
    private Date regComTime;
    //注册资金（万元）
    private String regInvest;
    //注册号
    private String regNo;
    //项目合同备案时间
    private Date proContractTime;
    //项目移交注册时间
    private Date proRegTime;
    //项目申报市重大项目时间
    private Date regBigProTime;
    //项目认定市重大项目时间
    private Date regedBigProTime;
    //更新时间
    private Date updateTime;
    //项目交办情况:1.已交办;2.未交办;
    private String proType;
    //项目履约监管单位
    private String controlCom;
    //项目履约情况:1.正常履约;2.不正常履约
    private String proConventionType;
    //情况说明
    private String proConventionInfo;
    //下一步工作建议
    private String nextAdvise;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProConcatCom() {
        return proConcatCom;
    }

    public void setProConcatCom(String proConcatCom) {
        this.proConcatCom = proConcatCom;
    }

    public String getRegComName() {
        return regComName;
    }

    public void setRegComName(String regComName) {
        this.regComName = regComName;
    }

    public Date getRegComTime() {
        return regComTime;
    }

    public void setRegComTime(Date regComTime) {
        this.regComTime = regComTime;
    }

    public String getRegInvest() {
        return regInvest;
    }

    public void setRegInvest(String regInvest) {
        this.regInvest = regInvest;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public Date getProContractTime() {
        return proContractTime;
    }

    public void setProContractTime(Date proContractTime) {
        this.proContractTime = proContractTime;
    }

    public Date getProRegTime() {
        return proRegTime;
    }

    public void setProRegTime(Date proRegTime) {
        this.proRegTime = proRegTime;
    }

    public String getControlCom() {
        return controlCom;
    }

    public void setControlCom(String controlCom) {
        this.controlCom = controlCom;
    }

    public String getProConventionType() {
        return proConventionType;
    }

    public void setProConventionType(String proConventionType) {
        this.proConventionType = proConventionType;
    }

    public String getProConventionInfo() {
        return proConventionInfo;
    }

    public void setProConventionInfo(String proConventionInfo) {
        this.proConventionInfo = proConventionInfo;
    }

    public String getNextAdvise() {
        return nextAdvise;
    }

    public void setNextAdvise(String nextAdvise) {
        this.nextAdvise = nextAdvise;
    }

    public Date getRegBigProTime() {
        return regBigProTime;
    }

    public void setRegBigProTime(Date regBigProTime) {
        this.regBigProTime = regBigProTime;
    }

    public Date getRegedBigProTime() {
        return regedBigProTime;
    }

    public void setRegedBigProTime(Date regedBigProTime) {
        this.regedBigProTime = regedBigProTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }
}
