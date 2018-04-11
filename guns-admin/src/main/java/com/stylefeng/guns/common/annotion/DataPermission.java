package com.stylefeng.guns.common.annotion;

import java.lang.annotation.*;

/**
 * 权限注解 用于检查权限 规定访问权限
 *
 * @example @Permission({roleID1,roleID2})
 * @example @Permission
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataPermission {
    String value();// value是需要做数据权限的实体的id
}
