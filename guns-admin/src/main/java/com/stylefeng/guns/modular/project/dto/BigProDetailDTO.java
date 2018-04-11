package com.stylefeng.guns.modular.project.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanhao on 2017/12/3.
 */
public class BigProDetailDTO {
    private Integer id;
    private String folType;//项目跟踪状态
    private String name;//投资项目名称
    private String status;//投资项目阶段
    private String followUserId;//跟踪人id
    private String followName;//跟踪人员名称
    private String followTel;//跟踪人员电话
    private String category;//归属产业:1.第一;2.第二;3.第三;
    private Date planStartTime;//计划开工日期
    private Date planEndTime;//计划竣工日期
    private String planProcess;//建议年底拟达到的工作进度
    private String leader;//分管区领导
    private Date updateWorkTime;//修改工作进度时间
    private String workProcess;//目前工作进度
    private String infoDesc;//备注
    private String investRmb;//匡算总投资额人民币(亿元)
    private String investMvRmb;//匡算总投资额拆迁费人民币(亿元)
    private String investDollar;//投资额(美元)
    private String investRatio;//投资额(汇率)
    private String content;//建设内容
    private String govArea;//国有建设用地(亩)
    private String collectArea;//集体建设用地(亩)
    private String farmArea;//农用地(亩)
    private List<Map> industryType;//具体行业分类
    private List<Map> packPosition;//策划包装定位
    private List<Map> uniliable;//责任单位及相关人信息
    private List<Map> adviseOpeType;//建议实施方式
    private List<Map> followPlat;//负责跟踪平台
    private List<Map> buildPlace;//拟建设地点
    private List<Map> chanyeList;//归属产业集合
    /**
     * 创建人名
     */
    private String createUserName;

    private String createAccount;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFolType() {
        return folType;
    }

    public void setFolType(String folType) {
        this.folType = folType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getFollowTel() {
        return followTel;
    }

    public void setFollowTel(String followTel) {
        this.followTel = followTel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getPlanProcess() {
        return planProcess;
    }

    public void setPlanProcess(String planProcess) {
        this.planProcess = planProcess;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Date getUpdateWorkTime() {
        return updateWorkTime;
    }

    public void setUpdateWorkTime(Date updateWorkTime) {
        this.updateWorkTime = updateWorkTime;
    }

    public String getWorkProcess() {
        return workProcess;
    }

    public void setWorkProcess(String workProcess) {
        this.workProcess = workProcess;
    }

    public String getInfoDesc() {
        return infoDesc;
    }

    public void setInfoDesc(String infoDesc) {
        this.infoDesc = infoDesc;
    }

    public String getInvestRmb() {
        return investRmb;
    }

    public void setInvestRmb(String investRmb) {
        this.investRmb = investRmb;
    }

    public String getInvestMvRmb() {
        return investMvRmb;
    }

    public void setInvestMvRmb(String investMvRmb) {
        this.investMvRmb = investMvRmb;
    }

    public String getInvestDollar() {
        return investDollar;
    }

    public void setInvestDollar(String investDollar) {
        this.investDollar = investDollar;
    }

    public String getInvestRatio() {
        return investRatio;
    }

    public void setInvestRatio(String investRatio) {
        this.investRatio = investRatio;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGovArea() {
        return govArea;
    }

    public void setGovArea(String govArea) {
        this.govArea = govArea;
    }

    public String getCollectArea() {
        return collectArea;
    }

    public void setCollectArea(String collectArea) {
        this.collectArea = collectArea;
    }

    public String getFarmArea() {
        return farmArea;
    }

    public void setFarmArea(String farmArea) {
        this.farmArea = farmArea;
    }

    public List<Map> getIndustryType() {
        return industryType;
    }

    public void setIndustryType(List<Map> industryType) {
        this.industryType = industryType;
    }

    public List<Map> getPackPosition() {
        return packPosition;
    }

    public void setPackPosition(List<Map> packPosition) {
        this.packPosition = packPosition;
    }

    public List<Map> getUniliable() {
        return uniliable;
    }

    public void setUniliable(List<Map> uniliable) {
        this.uniliable = uniliable;
    }

    public List<Map> getAdviseOpeType() {
        return adviseOpeType;
    }

    public void setAdviseOpeType(List<Map> adviseOpeType) {
        this.adviseOpeType = adviseOpeType;
    }

    public List<Map> getFollowPlat() {
        return followPlat;
    }

    public void setFollowPlat(List<Map> followPlat) {
        this.followPlat = followPlat;
    }

    public List<Map> getBuildPlace() {
        return buildPlace;
    }

    public void setBuildPlace(List<Map> buildPlace) {
        this.buildPlace = buildPlace;
    }

    public List<Map> getChanyeList() {
        return chanyeList;
    }

    public void setChanyeList(List<Map> chanyeList) {
        this.chanyeList = chanyeList;
    }
}
