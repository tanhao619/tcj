package com.stylefeng.guns.core.datascope;


import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.CollectionKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据范围的拦截器
 *
 * @author fengshuonan
 * @date 2017-07-23 21:26
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();

        //查找参数中包含DataScope类型的参数
        DataScope dataScope = findDataScopeObject(parameterObject);

        if (dataScope == null) {
            //String loginUsername = (String)ShiroKit.getSession().getAttribute("loginUsername");
            originalSql = "select * from (" + originalSql + ") temp_data_scope " ;
            metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
            return invocation.proceed();
        } else {
            String scopeName = dataScope.getScopeName();
            String relationTable = dataScope.getRelationTable();
            String relationField = dataScope.getRelationField();
            String orderField = dataScope.getOrderField();
            Boolean isASC = dataScope.getASC();
            List<Integer> deptIds = dataScope.getDeptIds();
            String join = CollectionKit.join(deptIds, ",");
            if (isRelation(relationTable,relationField)){
                if (CollectionUtils.isEmpty(deptIds)){
                    originalSql = "select * from (" + originalSql + ") temp_data_scope " +
                            "where temp_data_scope.id in (SELECT "+ relationField +" FROM " + relationTable +" temp_relation_scope WHERE temp_relation_scope."+scopeName+" in (-1))";
                }else {
                    originalSql = "select * from (" + originalSql + ") temp_data_scope " +
                            "where temp_data_scope.id in (SELECT "+ relationField +" FROM " + relationTable +" temp_relation_scope WHERE temp_relation_scope."+scopeName+" in (" + join + "))";
                }
            }else {
                originalSql = "select * from (" + originalSql + ") temp_data_scope where temp_data_scope." + scopeName + " in (" + join + ")";
            }
            try {
                //添加跟踪人能够访问
                if (originalSql.contains("p.followUserId as Follow_User_Id")) {
                    originalSql += " or temp_data_scope.Follow_User_Id = " + ShiroKit.getUser().getId();
                }
                //添加项目分类
                if (originalSql.contains("as FOLLOW_TYPE")) {
                    originalSql = originalSql.substring(0, originalSql.lastIndexOf(')'));
                    originalSql += " and  folType = FOLLOW_TYPE )";
                }
            } catch (Exception e) {
                RecordLogTools.error("DataScopeInterceptor拦截器未获取到用户id", e);
            }
            // 如果有排序
            if (StringUtils.hasText(orderField)){
                String orderSql = escapOrder(orderField,isASC);
                originalSql += orderSql;
            }
            metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
            return invocation.proceed();
        }
    }

    /**
     * 查找参数是否包括DataScope对象
     */
    public DataScope findDataScopeObject(Object parameterObj) {
        if (parameterObj instanceof DataScope) {
            return (DataScope) parameterObj;
        } else if (parameterObj instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObj).values()) {
                if (val instanceof DataScope) {
                    return (DataScope) val;
                }
            }
        }
        return null;
    }
    private Boolean isRelation(String relationTable ,String relationField){
        Boolean isRelation = false;
        if (StringUtils.hasText(relationTable) &&
                StringUtils.hasText(relationField)){
            isRelation = true;
        }
        return isRelation;
    }

    private String escapOrder(String orderField ,Boolean isASC){
        String reOrderSql = "";
        if (StringUtils.hasText(orderField) ){
            if (isASC){
                reOrderSql = " ORDER BY temp_data_scope." + orderField + " ASC";
            }else
                reOrderSql = " ORDER BY temp_data_scope." +  orderField + " DESC";
        }
        return reOrderSql;
    }
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
