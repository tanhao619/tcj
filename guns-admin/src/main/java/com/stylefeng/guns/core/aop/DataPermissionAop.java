/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stylefeng.guns.core.aop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.annotion.DataPermission;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.FollowProject;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.check.PermissionCheckManager;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.project.service.IFollowProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AOP 权限自定义检查
 */
@Aspect
@Component
public class DataPermissionAop {

    @Pointcut(value = "@annotation(com.stylefeng.guns.common.annotion.DataPermission)")
    private void cutPermission() {

    }
    @Around("cutPermission()")
    public Object doPermission(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        DataPermission dataPermission = method.getAnnotation(DataPermission.class);
        if (ShiroKit.isAdmin()){// 超级管理员直接放开
            return point.proceed();
        }
        String value = dataPermission.value();
        if (StringUtils.hasText(value)){
            HttpServletRequest request = HttpKit.getRequest();
            String idStr = request.getParameter(value);
            if (StringUtils.hasText(idStr)){
                if (isCanParseId(idStr)){
                    Integer id = Integer.parseInt(idStr);
                    List<Integer> proIds = ShiroKit.getUser().getProIds();
                    if (CollectionUtils.isNotEmpty(proIds)){
                        if (proIds.contains(id)){
                            return point.proceed();
                        }else {
                            RecordLogTools.info("用户没有对应的数据权限！");
                            return new FailTip(403,"用户没有对应的数据权限！");
                        }
                    }else {
                        RecordLogTools.info("用户没有对应的数据权限！");
                        return new FailTip(403,"用户没有对应的数据权限！");
                    }
                }else {
                    RecordLogTools.info("id格式错误!");
                    return new FailTip(400,"id格式错误!");
                }
            }else {
                RecordLogTools.info("@DataPermission注解value错误，未匹配上request中参数的key 或参数key传值为null");
                throw new NoPermissionException("@DataPermission注解value错误，未匹配上request中参数的key 或参数key对应值为null");
            }
        }else {
            RecordLogTools.info("@DataPermission注解value值不能为null");
            //return new FailTip(400,"@DataPermission注解value值不能为null");
            throw new NoPermissionException("@DataPermission注解value值不能为null");
        }
    }

    private Boolean isCanParseId(String strId) {
        Boolean rebool = false;
        try {
            Integer.parseInt(strId);
            rebool = true;
        } catch (Exception e) {
        }
        return rebool;
    }
}
