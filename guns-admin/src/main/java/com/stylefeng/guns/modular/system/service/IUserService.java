package com.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色相关业务
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午9:11:57
 */
public interface IUserService extends IService<User> {
    /**
     *通过科室 simplename、tips 角色duty 确定user
     */
    List<User> getUserByNTD(@Param("simplename")String deptSimplename, @Param("tips")String deptTips, @Param("duty")String roleDuty);

    /**
     * 通过account 查询用户
     */
    User selectUserByAccount(String account);

    /**
     * 分配角色：分配的角色中是否不只包含超级管理员
     */
    Boolean isNotOnlyContainAdmin(String[] strRoleIds);
}
