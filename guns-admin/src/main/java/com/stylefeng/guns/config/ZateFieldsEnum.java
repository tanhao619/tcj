package com.stylefeng.guns.config;

public enum ZateFieldsEnum {
    // 默认字段
    //ComPany(-2,"","公司名称"),


    // 慈善
    CiShanTouRuE(1,"charitableInvestment","慈善投入额"),
    CiShanTouRuCiShu(1,"charitableEvents","慈善投入额"),

    // 创新
    YanFaProjectNum(2,"projectsUnderDevelopment","研发项目数"),// = 正在研发项目数
    SalesRevenueNew(2,"salesRevenueNew","创新产品销售额"),// = 新产品销售收入
    InvestMent(2,"investment","研发投入额"),

    // 资产
    TotalAssets(3,"totalAssets","资产总额"),
    FuZaiZongE(3,"liabilitiesEnding","负债总额"),
    LiquidAssets(3,"liquidAssets","流动资产"),
    TotalIncome(3,"totalIncome","总资产收益率"),
    NetIncome(3,"netIncome","净资产收益率"),
    LiquidLiabilities(3,"liquidLiabilities","流动负债额"),


    // 收入&利润
    SalesTevenueTotal(4,"salesTevenueTotal","销售收入"),
    SalesProfit(4,"salesProfit","销售利润"),
    NetProfit(4,"netProfit","净利润"),
    MainBusinessIncome(4,"mainBusinessIncome","主营业务收入"),
    MainBusinessProfit(4,"mainBusinessProfit","主营业务利润");

    private Integer type;
    private String field;
    private String fieldName;


    private ZateFieldsEnum(Integer type, String field, String fieldName) {
        this.type = type;
        this.field = field;
        this.fieldName = fieldName;
    }

    public Integer getType() {
        return type;
    }

    public String getField() {
        return field;
    }

    public String getFieldName() {
        return fieldName;
    }
}
