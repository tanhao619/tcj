package com.stylefeng.guns.modular.oa.dto;

public class SWFlowDTO {

    /**
     * 流程步骤,均+1
     */
    private String step;
    /*------------------发起start-----------------------*/

    /**
     * 发起人
     */
    private String faqiUser;
    /**
     * 发起科室
     */
    private String faqiDept;
    /**
     * 收文编号
     */
    private String ordnumSW;

    /**
     * 提交时间（发起时间）
     */
    private String faqiTime;

    /*------------------发起end------------------------*/


    /*------------------审批start-----------------------*/
    /**
     * 审批人
     */
    private String spUser;
    /**
     * 审批科室:一个或多个，当流程是单流程只一个，是多流程是多个！
     */
    private String spDepts;
    /**
     * 审批意见:0 或 同意或不同意
     * 当为0 的时候证明审批处于待审批状态，与外部封装的type  不同意btn/同意btn， 还是只显示同意
     */
    private String spAdvice;
    /**
     * 被分配人：单流程一个，多流程多个
     */
    private String fenPeiUsers;
    /**
     * 提交时间(发起时间)
     */
    private String spTime;
    /*------------------审批end-------------------------*/




     /*------------------办结start-------------------------*/
    /**
     * 办结科员
     * @return
     */
    private String bjKeYuan;
    /**
     * 办结科室
     */
    private String bjDept;
    /**
     * 办结详情
     */
    private String bjDetail;
    /**
     * 是否办结
     * @return
     */
    private String isBanjie;

    /**
     * 办结时间
     * @return
     */
    private String bjTime;
    /*------------------办结end-------------------------*/

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getFaqiUser() {
        return faqiUser;
    }

    public void setFaqiUser(String faqiUser) {
        this.faqiUser = faqiUser;
    }

    public String getFaqiDept() {
        return faqiDept;
    }

    public void setFaqiDept(String faqiDept) {
        this.faqiDept = faqiDept;
    }

    public String getOrdnumSW() {
        return ordnumSW;
    }

    public void setOrdnumSW(String ordnumSW) {
        this.ordnumSW = ordnumSW;
    }

    public String getFaqiTime() {
        return faqiTime;
    }

    public void setFaqiTime(String faqiTime) {
        this.faqiTime = faqiTime;
    }

    public String getSpUser() {
        return spUser;
    }

    public void setSpUser(String spUser) {
        this.spUser = spUser;
    }

    public String getSpDepts() {
        return spDepts;
    }

    public void setSpDepts(String spDepts) {
        this.spDepts = spDepts;
    }

    public String getSpAdvice() {
        return spAdvice;
    }

    public void setSpAdvice(String spAdvice) {
        this.spAdvice = spAdvice;
    }

    public String getFenPeiUsers() {
        return fenPeiUsers;
    }

    public void setFenPeiUsers(String fenPeiUsers) {
        this.fenPeiUsers = fenPeiUsers;
    }

    public String getSpTime() {
        return spTime;
    }

    public void setSpTime(String spTime) {
        this.spTime = spTime;
    }

    public String getBjKeYuan() {
        return bjKeYuan;
    }

    public void setBjKeYuan(String bjKeYuan) {
        this.bjKeYuan = bjKeYuan;
    }

    public String getBjDept() {
        return bjDept;
    }

    public void setBjDept(String bjDept) {
        this.bjDept = bjDept;
    }

    public String getBjDetail() {
        return bjDetail;
    }

    public void setBjDetail(String bjDetail) {
        this.bjDetail = bjDetail;
    }

    public String getIsBanjie() {
        return isBanjie;
    }

    public void setIsBanjie(String isBanjie) {
        this.isBanjie = isBanjie;
    }

    public String getBjTime() {
        return bjTime;
    }

    public void setBjTime(String bjTime) {
        this.bjTime = bjTime;
    }
}
