package com.stylefeng.guns.config;

public enum ZateLandEnum {
    // 默认字段
    Ordinal("id","编号"),

    // 主体字段
    Name("name","载体资源名称"),
    Address("address","资源地点"),
    Area("area","资源面积（亩）"),
    //资源状态:1.未使用;2.已使用;3.已停用;
    Status("status","资源状态"),
    //用地性质:1.国有土地;2.集体土地;3.农用地;4.建设用地;5.未用地;
    Property("property","用地性质"),
    //入驻产业类别:1.第一产业;2.第二产业;3.第三产业;
    InduPosition("induPosition","规划产业定位"),
    Situation("situation","地块现状"),
    Condition("condition","规划设计条件"),
    Unit("unit","填报单位"),
    FillTime("fillTime","填报时间"),
    FillContacter("fillContacter","填报人姓名"),
    FillTel("fillTel","填报人电话"),
    Remark("remark","备注"),
    CreateTime("createTime","创建时间"),
    CreateUser("createUser","创建人");
    // 保留字段 createUserId 、updateUserId 、updateTime、currentTime



    private String field;
    private String fieldName;


    private ZateLandEnum(String field, String fieldName) {
        this.field = field;
        this.fieldName = fieldName;
    }


    public String getField() {
        return field;
    }

    public String getFieldName() {
        return fieldName;
    }
}
