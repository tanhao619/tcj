package com.stylefeng.guns.core.datascope;

import com.stylefeng.guns.core.shiro.ShiroKit;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 数据范围
 *
 * @author fengshuonan
 * @date 2017-07-23 22:19
 */
public class  DataScope {

    /**
     * 限制范围的字段名称
     */
    private String scopeName = "deptid";

    /**
     * 多对多关联限制：关联表
     */
    private String relationTable = "";
    /**
     * 多对多关联限制：关联字段
     */
    private String relationField = "";

    /**
     * 排序字段
     * @return
     */
    private String orderField = "";

    /**
     * 排序方式 默认ASC
     * @return
     */
    private boolean isASC  = true;

    public String getRelationTable() {
        return relationTable;
    }

    public void setRelationTable(String relationTable) {
        this.relationTable = relationTable;
    }

    public String getRelationField() {
        return relationField;
    }

    public void setRelationField(String relationField) {
        this.relationField = relationField;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public boolean getASC() {
        return isASC;
    }

    public void setASC(boolean ASC) {
        isASC = ASC;
    }

    /**
     * 具体的数据范围
     */
    private List<Integer> deptIds;

    public DataScope() {
    }
    // 有排序
   public static DataScope getInstance(String orderField,boolean isASC,Map<String, Object> paraMap) {
       DataScope dataScope = new DataScope();
       //  超级管理员跟局长可以看所有项目
       if (ShiroKit.isAdmin() || ShiroKit.isJuZhang()) {
           dataScope = null;
       }else {
           dataScope.setScopeName("deptId");
           dataScope.setDeptIds(ShiroKit.getUser().getDeptIds());
           dataScope.setRelationTable("follow_project");
           dataScope.setRelationField("proId");
           String parmOrderField = (String)paraMap.get("sort");
           String parmOrderSort = (String) paraMap.get("order");
           if (StringUtils.hasText(parmOrderField)&& StringUtils.hasText(parmOrderSort)){
               // 前台传排序
               dataScope.setOrderField(parmOrderField);
               if ("asc".equals(parmOrderSort)){
                   dataScope.setASC(true);
               }
               if ("desc".equals(parmOrderSort)){
                   dataScope.setASC(false);
               }

           }else{// 默认排序
               dataScope.setOrderField(orderField);
               dataScope.setASC(isASC);
           }

       }
       return dataScope;
   }

   // 无排序
    public static DataScope getInstance() {
        DataScope dataScope = new DataScope();
        if (ShiroKit.isAdmin()) {
            dataScope = null;
        }else {
            dataScope.setScopeName("deptId");
            dataScope.setDeptIds(ShiroKit.getUser().getDeptIds());
            dataScope.setRelationTable("follow_project");
            dataScope.setRelationField("proId");
        }
        return dataScope;
    }



    public DataScope(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public DataScope(String scopeName, List<Integer> deptIds) {
        this.scopeName = scopeName;
        this.deptIds = deptIds;
    }

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }
}
