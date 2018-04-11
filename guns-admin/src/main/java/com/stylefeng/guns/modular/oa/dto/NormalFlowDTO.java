package com.stylefeng.guns.modular.oa.dto;

import java.util.Date;

public class NormalFlowDTO {

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
     * 审批科室
     */
    private String spDept;
    /**
     * 审批意见:0 或 同意或不同意
     * 当为0 的时候证明审批处于待审批状态，与外部封装的type  不同意btn/同意btn， 还是只显示同意
     */
    private String spAdvice;
    /**
     * 提交时间(发起时间)
     */
    private String spTime;
    /*------------------审批end-------------------------*/

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

    public String getSpDept() {
        return spDept;
    }

    public void setSpDept(String spDept) {
        this.spDept = spDept;
    }

    public String getSpAdvice() {
        return spAdvice;
    }

    public void setSpAdvice(String spAdvice) {
        this.spAdvice = spAdvice;
    }

    public String getSpTime() {
        return spTime;
    }

    public void setSpTime(String spTime) {
        this.spTime = spTime;
    }
}
