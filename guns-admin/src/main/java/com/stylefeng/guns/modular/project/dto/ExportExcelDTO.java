package com.stylefeng.guns.modular.project.dto;

/**
 * Created by Tanhao on 2017/11/20.
 */
public class ExportExcelDTO {
    /**
     * 项目基本信息
     */
    private String id = "";
    private String comName = "";
    private String comComType = "";
    private String comAddr = "";
    private String comContent = "";
    private String comAuthor = "";
    private String comTel = "";
    private String companyInfo = "";
    private String norName = "";
    private String norContent = "";
    private String norFromArea = "";
    private String norInvestType = "";
    private String norCategory = "";
    private String norLeadCategory = "";
    private String norEnterOfferInfo = "";
    private String norEnterOfferName = "";
    private String norEnterOfferTel = "";
    private String norProInfo = "";
    private String norAuthor = "";
    private String norTel = "";
    private String folDeptId = "";
    private String norFollowInfo = "";
    private String norFollowName = "";
    private String norFollowTel = "";
    private String proAddr = "";
    private String proUseArea = "";
    private String norInvestRmb = "";
    private String norInvestDollar = "";
    private String norInvestRatio = "";
    private String norIsBigPro = "";
    private String norLeader = "";
    private String uniLiable = "";
    private String uniInfo = "";
    private String uniName = "";
    private String uniTel = "";
    private String norBigProCom = "";
    private String norBigProTime = "";
    private String norStatus = "";
    private String advName = "";
    /**
     * 项目洽谈与签约信息
     */
    private String norFirstTalkTime = "";
    private String talkProgress = "";
    private String talkQuestion = "";
    private String talkNextStep = "";
    private String talkIsvisit = "";
    private String talkVisitLv = "";
    private String norProMeetTime = "";
    private String norProViewTime = "";
    private String norContractTime = "";
    private String conContractType = "";
    private String norControlCom = "";
    private String norProContractTime = "";
    private String norProRegTime = "";
    private String norProType = "";
    private String norRegBigProTime = "";
    private String norRegedBigProTime = "";
    /**
     * 项目履约信息
     */
    private String norProConcatCom = "";
    private String norRegComName = "";
    private String norRegComTime = "";
    private String norRegInvest = "";
    private String norRegNo = "";
    private String norProConventionType = "";
    private String conProConventionInfo = "";
    private String conNextAdvise = "";

    private String norInfoDesc = "";

    public ExportExcelDTO() {
    }

    public ExportExcelDTO(String id,String comName, String comComType, String comAddr, String comContent, String companyInfo,
                          String norName, String norContent, String norFromArea, String norInvestType, String norCategory,
                          String norLeadCategory, String norEnterOfferInfo, String norProInfo, String folDeptId, String norFollowInfo,
                          String proAddr, String proUseArea, String norInvestRmb, String norInvestDollar, String norInvestRatio, String norIsBigPro,
                          String norLeader, String uniLiable, String uniInfo, String norBigProCom, String norBigProTime, String norStatus,String advName, String norFirstTalkTime,
                          String talkProgress, String talkQuestion, String talkNextStep, String talkIsvisit, String talkVisitLv, String norProMeetTime, String norProViewTime,
                          String norContractTime, String conContractType, String norControlCom, String norProContractTime, String norProRegTime, String norProType,
                          String norRegBigProTime, String norRegedBigProTime, String norProConcatCom, String norRegComName, String norRegComTime, String norRegInvest,
                          String norRegNo, String norProConventionType, String conProConventionInfo, String conNextAdvise, String norInfoDesc) {
        this.id = id;
        this.comName = comName;
        this.comComType = comComType;
        this.comAddr = comAddr;
        this.comContent = comContent;
        this.companyInfo = companyInfo;
        this.norName = norName;
        this.norContent = norContent;
        this.norFromArea = norFromArea;
        this.norInvestType = norInvestType;
        this.norCategory = norCategory;
        this.norLeadCategory = norLeadCategory;
        this.norEnterOfferInfo = norEnterOfferInfo;
        this.norProInfo = norProInfo;
        this.folDeptId = folDeptId;
        this.norFollowInfo = norFollowInfo;
        this.proAddr = proAddr;
        this.proUseArea = proUseArea;
        this.norInvestRmb = norInvestRmb;
        this.norInvestDollar = norInvestDollar;
        this.norInvestRatio = norInvestRatio;
        this.norIsBigPro = norIsBigPro;
        this.norLeader = norLeader;
        this.uniLiable = uniLiable;
        this.uniInfo = uniInfo;
        this.norBigProCom = norBigProCom;
        this.norBigProTime = norBigProTime;
        this.norStatus = norStatus;
        this.advName = advName;
        this.norFirstTalkTime = norFirstTalkTime;
        this.talkProgress = talkProgress;
        this.talkQuestion = talkQuestion;
        this.talkNextStep = talkNextStep;
        this.talkIsvisit = talkIsvisit;
        this.talkVisitLv = talkVisitLv;
        this.norProMeetTime = norProMeetTime;
        this.norProViewTime = norProViewTime;
        this.norContractTime = norContractTime;
        this.conContractType = conContractType;
        this.norControlCom = norControlCom;
        this.norProContractTime = norProContractTime;
        this.norProRegTime = norProRegTime;
        this.norProType = norProType;
        this.norRegBigProTime = norRegBigProTime;
        this.norRegedBigProTime = norRegedBigProTime;
        this.norProConcatCom = norProConcatCom;
        this.norRegComName = norRegComName;
        this.norRegComTime = norRegComTime;
        this.norRegInvest = norRegInvest;
        this.norRegNo = norRegNo;
        this.norProConventionType = norProConventionType;
        this.conProConventionInfo = conProConventionInfo;
        this.conNextAdvise = conNextAdvise;
        this.norInfoDesc = norInfoDesc;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComComType() {
        return comComType;
    }

    public void setComComType(String comComType) {
        this.comComType = comComType;
    }

    public String getComAddr() {
        return comAddr;
    }

    public void setComAddr(String comAddr) {
        this.comAddr = comAddr;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public String getComAuthor() {
        return comAuthor;
    }

    public void setComAuthor(String comAuthor) {
        this.comAuthor = comAuthor;
    }

    public String getComTel() {
        return comTel;
    }

    public void setComTel(String comTel) {
        this.comTel = comTel;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getNorName() {
        return norName;
    }

    public void setNorName(String norName) {
        this.norName = norName;
    }

    public String getNorContent() {
        return norContent;
    }

    public void setNorContent(String norContent) {
        this.norContent = norContent;
    }

    public String getNorFromArea() {
        return norFromArea;
    }

    public void setNorFromArea(String norFromArea) {
        this.norFromArea = norFromArea;
    }

    public String getNorInvestType() {
        return norInvestType;
    }

    public void setNorInvestType(String norInvestType) {
        this.norInvestType = norInvestType;
    }

    public String getNorCategory() {
        return norCategory;
    }

    public void setNorCategory(String norCategory) {
        this.norCategory = norCategory;
    }

    public String getNorLeadCategory() {
        return norLeadCategory;
    }

    public void setNorLeadCategory(String norLeadCategory) {
        this.norLeadCategory = norLeadCategory;
    }

    public String getNorEnterOfferInfo() {
        return norEnterOfferInfo;
    }

    public void setNorEnterOfferInfo(String norEnterOfferInfo) {
        this.norEnterOfferInfo = norEnterOfferInfo;
    }

    public String getNorEnterOfferName() {
        return norEnterOfferName;
    }

    public void setNorEnterOfferName(String norEnterOfferName) {
        this.norEnterOfferName = norEnterOfferName;
    }

    public String getNorEnterOfferTel() {
        return norEnterOfferTel;
    }

    public void setNorEnterOfferTel(String norEnterOfferTel) {
        this.norEnterOfferTel = norEnterOfferTel;
    }

    public String getNorProInfo() {
        return norProInfo;
    }

    public void setNorProInfo(String norProInfo) {
        this.norProInfo = norProInfo;
    }

    public String getNorAuthor() {
        return norAuthor;
    }

    public void setNorAuthor(String norAuthor) {
        this.norAuthor = norAuthor;
    }

    public String getNorTel() {
        return norTel;
    }

    public void setNorTel(String norTel) {
        this.norTel = norTel;
    }

    public String getFolDeptId() {
        return folDeptId;
    }

    public void setFolDeptId(String folDeptId) {
        this.folDeptId = folDeptId;
    }

    public String getNorFollowInfo() {
        return norFollowInfo;
    }

    public void setNorFollowInfo(String norFollowInfo) {
        this.norFollowInfo = norFollowInfo;
    }

    public String getNorFollowName() {
        return norFollowName;
    }

    public void setNorFollowName(String norFollowName) {
        this.norFollowName = norFollowName;
    }

    public String getNorFollowTel() {
        return norFollowTel;
    }

    public void setNorFollowTel(String norFollowTel) {
        this.norFollowTel = norFollowTel;
    }

    public String getProAddr() {
        return proAddr;
    }

    public void setProAddr(String proAddr) {
        this.proAddr = proAddr;
    }

    public String getProUseArea() {
        return proUseArea;
    }

    public void setProUseArea(String proUseArea) {
        this.proUseArea = proUseArea;
    }

    public String getNorInvestRmb() {
        return norInvestRmb;
    }

    public void setNorInvestRmb(String norInvestRmb) {
        this.norInvestRmb = norInvestRmb;
    }

    public String getNorInvestDollar() {
        return norInvestDollar;
    }

    public void setNorInvestDollar(String norInvestDollar) {
        this.norInvestDollar = norInvestDollar;
    }

    public String getNorIsBigPro() {
        return norIsBigPro;
    }

    public void setNorIsBigPro(String norIsBigPro) {
        this.norIsBigPro = norIsBigPro;
    }

    public String getNorLeader() {
        return norLeader;
    }

    public void setNorLeader(String norLeader) {
        this.norLeader = norLeader;
    }

    public String getUniLiable() {
        return uniLiable;
    }

    public void setUniLiable(String uniLiable) {
        this.uniLiable = uniLiable;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getNorBigProCom() {
        return norBigProCom;
    }

    public void setNorBigProCom(String norBigProCom) {
        this.norBigProCom = norBigProCom;
    }

    public String getNorBigProTime() {
        return norBigProTime;
    }

    public void setNorBigProTime(String norBigProTime) {
        this.norBigProTime = norBigProTime;
    }

    public String getNorStatus() {
        return norStatus;
    }

    public void setNorStatus(String norStatus) {
        this.norStatus = norStatus;
    }

    public String getNorFirstTalkTime() {
        return norFirstTalkTime;
    }

    public void setNorFirstTalkTime(String norFirstTalkTime) {
        this.norFirstTalkTime = norFirstTalkTime;
    }

    public String getTalkProgress() {
        return talkProgress;
    }

    public void setTalkProgress(String talkProgress) {
        this.talkProgress = talkProgress;
    }

    public String getTalkQuestion() {
        return talkQuestion;
    }

    public void setTalkQuestion(String talkQuestion) {
        this.talkQuestion = talkQuestion;
    }

    public String getTalkNextStep() {
        return talkNextStep;
    }

    public void setTalkNextStep(String talkNextStep) {
        this.talkNextStep = talkNextStep;
    }

    public String getTalkIsvisit() {
        return talkIsvisit;
    }

    public void setTalkIsvisit(String talkIsvisit) {
        this.talkIsvisit = talkIsvisit;
    }

    public String getTalkVisitLv() {
        return talkVisitLv;
    }

    public void setTalkVisitLv(String talkVisitLv) {
        this.talkVisitLv = talkVisitLv;
    }

    public String getNorProMeetTime() {
        return norProMeetTime;
    }

    public void setNorProMeetTime(String norProMeetTime) {
        this.norProMeetTime = norProMeetTime;
    }

    public String getNorProViewTime() {
        return norProViewTime;
    }

    public void setNorProViewTime(String norProViewTime) {
        this.norProViewTime = norProViewTime;
    }

    public String getNorContractTime() {
        return norContractTime;
    }

    public void setNorContractTime(String norContractTime) {
        this.norContractTime = norContractTime;
    }

    public String getConContractType() {
        return conContractType;
    }

    public void setConContractType(String conContractType) {
        this.conContractType = conContractType;
    }

    public String getNorControlCom() {
        return norControlCom;
    }

    public void setNorControlCom(String norControlCom) {
        this.norControlCom = norControlCom;
    }

    public String getNorProContractTime() {
        return norProContractTime;
    }

    public void setNorProContractTime(String norProContractTime) {
        this.norProContractTime = norProContractTime;
    }

    public String getNorProRegTime() {
        return norProRegTime;
    }

    public void setNorProRegTime(String norProRegTime) {
        this.norProRegTime = norProRegTime;
    }

    public String getNorProType() {
        return norProType;
    }

    public void setNorProType(String norProType) {
        this.norProType = norProType;
    }

    public String getNorRegBigProTime() {
        return norRegBigProTime;
    }

    public void setNorRegBigProTime(String norRegBigProTime) {
        this.norRegBigProTime = norRegBigProTime;
    }

    public String getNorRegedBigProTime() {
        return norRegedBigProTime;
    }

    public void setNorRegedBigProTime(String norRegedBigProTime) {
        this.norRegedBigProTime = norRegedBigProTime;
    }

    public String getNorProConcatCom() {
        return norProConcatCom;
    }

    public void setNorProConcatCom(String norProConcatCom) {
        this.norProConcatCom = norProConcatCom;
    }

    public String getNorRegComName() {
        return norRegComName;
    }

    public void setNorRegComName(String norRegComName) {
        this.norRegComName = norRegComName;
    }

    public String getNorRegComTime() {
        return norRegComTime;
    }

    public void setNorRegComTime(String norRegComTime) {
        this.norRegComTime = norRegComTime;
    }

    public String getNorRegInvest() {
        return norRegInvest;
    }

    public void setNorRegInvest(String norRegInvest) {
        this.norRegInvest = norRegInvest;
    }

    public String getNorRegNo() {
        return norRegNo;
    }

    public void setNorRegNo(String norRegNo) {
        this.norRegNo = norRegNo;
    }

    public String getNorProConventionType() {
        return norProConventionType;
    }

    public void setNorProConventionType(String norProConventionType) {
        this.norProConventionType = norProConventionType;
    }

    public String getConProConventionInfo() {
        return conProConventionInfo;
    }

    public void setConProConventionInfo(String conProConventionInfo) {
        this.conProConventionInfo = conProConventionInfo;
    }

    public String getConNextAdvise() {
        return conNextAdvise;
    }

    public void setConNextAdvise(String conNextAdvise) {
        this.conNextAdvise = conNextAdvise;
    }

    public String getNorInfoDesc() {
        return norInfoDesc;
    }

    public void setNorInfoDesc(String norInfoDesc) {
        this.norInfoDesc = norInfoDesc;
    }

    public String getNorInvestRatio() {
        return norInvestRatio;
    }

    public void setNorInvestRatio(String norInvestRatio) {
        this.norInvestRatio = norInvestRatio;
    }

    public String getUniInfo() {
        return uniInfo;
    }

    public void setUniInfo(String uniInfo) {
        this.uniInfo = uniInfo;
    }

    public String getUniTel() {
        return uniTel;
    }

    public void setUniTel(String uniTel) {
        this.uniTel = uniTel;
    }

    public String getAdvName() {
        return advName;
    }

    public void setAdvName(String advName) {
        this.advName = advName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
