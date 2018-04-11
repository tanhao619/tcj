package com.stylefeng.guns.core.shiro.factory;

import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.common.persistence.model.User;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import java.util.List;

/**
 * 定义shirorealm所需数据的接口
 *
 * @author fengshuonan
 * @date 2016年12月5日 上午10:23:34
 */
public interface IShiro {

    /**
     * 根据账号获取登录用户
     *
     * @param account 账号
     */
    User user(String account);

    /**
     * 根据系统用户获取Shiro的用户
     *
     * @param user 系统用户
     */
    ShiroUser shiroUser(User user);

    /**
     * 获取权限列表通过角色id
     *
     * @param roleId 角色id
     */
    List<String> findPermissionsByRoleId(Integer roleId);

    /**
     * 根据角色id获取角色名称
     *
     * @param roleId 角色id
     */
    String findRoleNameByRoleId(Integer roleId);

    /**
     * 获取shiro的认证信息
     */
    SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName);

    // add by lgg start
    // 登录人是否包含平台的单位
    public Boolean isAcountContainPingTai();

    // 登录人是否包含投促局信息中心的单位
    public Boolean isAcountContainInfoCenter();

    //  登录账号是否包含【办公室科员】的角色
    Boolean isAcountContainOfficeDept();
    //  登录账号是否包含任何投促局【科员】的角色
    Boolean isAcountContainTCJKeYuan();

    // 登录账号是否包含任何投促局【科长/副局长/局长】的角色 用于是否拥有 审批页面的判断
    Boolean isTCJKZoFJZoJZ();

    // 登录账号是否包含任何投促局科室
    Boolean isAcountContainTCJKeShi();

    // 登录账号是否包含任何投促局科室 排除局长局长
    Boolean isAcountContainTCJKeShiExcludeJZ();
    // 登录账号科室是否包含 信息中心
    Boolean isAcountContainXXZX();

    // 登录账号是否 只包含 信息中心
    Boolean isAcountOnlyContainXXZX();

    //登录账号是否是 办公室归档科员、收文发起科员为同一特定账号 收文/归档科员（登录名:swgd）
    Boolean isAccountSWGD();

    //登录账号是否是 确定办公室科长只为特定的一个账号：办公室主任 (登录名:bgszr)
    Boolean isAccountBGSZR();
    // add by lgg end

}
