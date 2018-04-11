package com.stylefeng.guns.modular.oa.dto;

public class UserFPDTO {
    /**
     * 被分配人id
     */
    private Integer userId;
    /**
     * 被分配人名字
     */
    private String userName;
    /**
     * 被分配人当前流程步骤所的科室id
     */
    private Integer deptId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
