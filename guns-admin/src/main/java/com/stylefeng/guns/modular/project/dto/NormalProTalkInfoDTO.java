package com.stylefeng.guns.modular.project.dto;

import com.stylefeng.guns.common.persistence.model.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Tanhao on 2017/11/17.
 */
public class NormalProTalkInfoDTO {
    private Integer id;
    //投资额：美元
    private String investDollar;
    //投资额：RMB
    private String investRmb;
    //汇率
    private String investRatio;
    //是否重大项目:1.是;0.否;
    private String isBigPro;
    //重大项目备案单位
    private String bigProCom;
    //重大项目备案时间
    private Date bigProTime;
    //项目备案表
    private List<ProAttachment> attachments;
    //分管区领导
    private String leader;
    //责任单位及相关责任人信息
    private List<UnitLiable> unitLiables;
    //项目地址及用地
    private List<ProArea> proAreas;
    //建议实施方式
    private List<ProAdviseOpeType> opeTypes;
    //项目初次洽谈时间
    private Date firstTalkTime;
    //更新时间
    private Date updateTime;
    //进展情况
    private String progress;
    //存在问题
    private String question;
    //下一步举措
    private String nextStep;
    //是否需要拜访
    private String isVisit;
    //是否需要拜访领导层级
    private String visitLv;
    //项目初审过会时间
    private Date proMeetTime;
    //项目审定过会时间
    private Date proViewTime;
    //项目合同签约时间
    private Date contractTime;
    //合同类型;
    private List<ProContractType> contractType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvestDollar() {
        return investDollar;
    }

    public void setInvestDollar(String investDollar) {
        this.investDollar = investDollar;
    }

    public String getInvestRmb() {
        return investRmb;
    }

    public void setInvestRmb(String investRmb) {
        this.investRmb = investRmb;
    }

    public String getInvestRatio() {
        return investRatio;
    }

    public void setInvestRatio(String investRatio) {
        this.investRatio = investRatio;
    }

    public String getIsBigPro() {
        return isBigPro;
    }

    public void setIsBigPro(String isBigPro) {
        this.isBigPro = isBigPro;
    }

    public String getBigProCom() {
        return bigProCom;
    }

    public void setBigProCom(String bigProCom) {
        this.bigProCom = bigProCom;
    }

    public Date getBigProTime() {
        return bigProTime;
    }

    public void setBigProTime(Date bigProTime) {
        this.bigProTime = bigProTime;
    }

    public Date getFirstTalkTime() {
        return firstTalkTime;
    }

    public void setFirstTalkTime(Date firstTalkTime) {
        this.firstTalkTime = firstTalkTime;
    }

    public List<ProAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ProAttachment> attachments) {
        this.attachments = attachments;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public List<UnitLiable> getUnitLiables() {
        return unitLiables;
    }

    public void setUnitLiables(List<UnitLiable> unitLiables) {
        this.unitLiables = unitLiables;
    }

    public List<ProArea> getProAreas() {
        return proAreas;
    }

    public void setProAreas(List<ProArea> proAreas) {
        this.proAreas = proAreas;
    }

    public List<ProAdviseOpeType> getOpeTypes() {
        return opeTypes;
    }

    public void setOpeTypes(List<ProAdviseOpeType> opeTypes) {
        this.opeTypes = opeTypes;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public String getIsVisit() {
        return isVisit;
    }

    public void setIsVisit(String isVisit) {
        this.isVisit = isVisit;
    }

    public String getVisitLv() {
        return visitLv;
    }

    public void setVisitLv(String visitLv) {
        this.visitLv = visitLv;
    }

    public Date getProMeetTime() {
        return proMeetTime;
    }

    public void setProMeetTime(Date proMeetTime) {
        this.proMeetTime = proMeetTime;
    }

    public Date getProViewTime() {
        return proViewTime;
    }

    public void setProViewTime(Date proViewTime) {
        this.proViewTime = proViewTime;
    }

    public Date getContractTime() {
        return contractTime;
    }

    public void setContractTime(Date contractTime) {
        this.contractTime = contractTime;
    }

    public List<ProContractType> getContractType() {
        return contractType;
    }

    public void setContractType(List<ProContractType> contractType) {
        this.contractType = contractType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
