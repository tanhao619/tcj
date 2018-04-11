package com.stylefeng.guns.modular.oa.dto;

public class QJTimePropDTO {
    /**
     * 请假开始时间
     */
    private String qjTimeB;
    /**
     * 请假开始时间的AMPM
     */
    private String qjAMPMB;
    /**
     * 请假结束时间
     */
    private String qjTimeE;
    /**
     * 请假结束时间的AMPM
     */
    private String qjAMPME;

    /**
     * 请假总时间
     */
    private String qjTotalDays;
    /**
     * 请假开始时间、AMPM 是否与 请假结束时间、AMPM相同的标识
     * 1相同，0不相同
     */
    private String isSame;

    public String getQjTotalDays() {
        return qjTotalDays;
    }

    public void setQjTotalDays(String qjTotalDays) {
        this.qjTotalDays = qjTotalDays;
    }

    public String getQjTimeB() {
        return qjTimeB;
    }

    public void setQjTimeB(String qjTimeB) {
        this.qjTimeB = qjTimeB;
    }

    public String getQjAMPMB() {
        return qjAMPMB;
    }

    public void setQjAMPMB(String qjAMPMB) {
        this.qjAMPMB = qjAMPMB;
    }

    public String getQjTimeE() {
        return qjTimeE;
    }

    public void setQjTimeE(String qjTimeE) {
        this.qjTimeE = qjTimeE;
    }

    public String getQjAMPME() {
        return qjAMPME;
    }

    public void setQjAMPME(String qjAMPME) {
        this.qjAMPME = qjAMPME;
    }

    public String getIsSame() {
        return isSame;
    }

    public void setIsSame(String isSame) {
        this.isSame = isSame;
    }
}
