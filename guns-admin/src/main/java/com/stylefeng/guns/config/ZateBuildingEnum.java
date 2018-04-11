package com.stylefeng.guns.config;

public enum ZateBuildingEnum {
    // 默认字段
    Ordinal("id","编号"),

    // 主体字段
    Name("name","载体资源名称"),
    Address("address","资源具体地址"),
    Area("area","建筑面积（平方米）"),
    // 资源状态:1.未使用;2.已使用;3.已停用;
    Status("status","资源状态"),
    //产权性质:1.出租;2.出售;3.租售一体;
    Property("property","产权性质"),
    //入驻产业类别:1.第一产业;2.第二产业;3.第三产业;
    InduType("induType","入驻产业类别"),
    Owne("owne","权属"),
    OwneContacter("owneContacter","权属联系人"),
    OwneTel("owneTel","权属人电话"),
    Condition("condition","入驻条件"),
    Unit("unit","填报单位"),
    FillTime("fillTime","填报时间"),
    FillContacter("fillContacter","填报人姓名"),
    FillTel("fillTel","填报人电话"),
    Remark("remark","备注"),
    CreateTime("createTime","创建时间"),
    CreateUser("createUser","创建人");
    // 保留字段 createUserId 、updateUserId、updateTime




    private String field;
    private String fieldName;


    private ZateBuildingEnum( String field, String fieldName) {
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
