package com.stylefeng.guns.modular.oa.dto;

import java.util.List;

public class SWFPUsersDTO {
    private List<UserFPDTO> JzSWFPUsers;

    private List<UserFPDTO> FjzSWFPUsers;

    private List<UserFPDTO> commSWFPUsers;

    private String nowStep;

    public String getNowStep() {
        return nowStep;
    }

    public void setNowStep(String nowStep) {
        this.nowStep = nowStep;
    }

    public List<UserFPDTO> getJzSWFPUsers() {
        return JzSWFPUsers;
    }

    public void setJzSWFPUsers(List<UserFPDTO> jzSWFPUsers) {
        JzSWFPUsers = jzSWFPUsers;
    }

    public List<UserFPDTO> getFjzSWFPUsers() {
        return FjzSWFPUsers;
    }

    public void setFjzSWFPUsers(List<UserFPDTO> fjzSWFPUsers) {
        FjzSWFPUsers = fjzSWFPUsers;
    }

    public List<UserFPDTO> getCommSWFPUsers() {
        return commSWFPUsers;
    }

    public void setCommSWFPUsers(List<UserFPDTO> commSWFPUsers) {
        this.commSWFPUsers = commSWFPUsers;
    }
}
