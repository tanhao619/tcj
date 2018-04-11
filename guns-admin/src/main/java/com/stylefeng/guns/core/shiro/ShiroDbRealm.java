package com.stylefeng.guns.core.shiro;

import com.stylefeng.guns.core.shiro.factory.IShiro;
import com.stylefeng.guns.core.shiro.factory.ShiroFactroy;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.common.persistence.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroDbRealm extends AuthorizingRealm {

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        IShiro shiroFactory = ShiroFactroy.me();
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = shiroFactory.user(token.getUsername());
        ShiroUser shiroUser = shiroFactory.shiroUser(user);
        SimpleAuthenticationInfo info = shiroFactory.info(shiroUser, user, super.getName());
        return info;
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        IShiro shiroFactory = ShiroFactroy.me();
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        List<Integer> roleList = shiroUser.getRoleList();

        Set<String> permissionSet = new HashSet<>();
        Set<String> roleNameSet = new HashSet<>();

        for (Integer roleId : roleList) {
            List<String> permissions = shiroFactory.findPermissionsByRoleId(roleId);
            if (permissions != null) {
                for (String permission : permissions) {
                    if (ToolUtil.isNotEmpty(permission)) {
                        permissionSet.add(permission);
                    }
                }
            }

            //add by lgg start
            Boolean isContainPingTai = shiroFactory.isAcountContainPingTai();
            Boolean isContainInfoCenter = shiroFactory.isAcountContainInfoCenter();
            // 1、如果登录账号包含【办公室科员】的角色,收文审批类型才显示
            Boolean isContainOfficeDept = shiroFactory.isAcountContainOfficeDept();
            // 2、如果登录账号包含任何投促局【科员】的角色，发文审批类型才显示
            Boolean isContainTCJKeYuan = shiroFactory.isAcountContainTCJKeYuan();
            //// 登录账号是否包含任何投促局【科长/副局长/局长】的角色 用于是否拥有 审批页面的判断
            Boolean isAnyTCJKZoFJZoJZ = shiroFactory.isTCJKZoFJZoJZ();
            // 登录账号是否包含任何投促局科室
            Boolean isContainTCJKeShi = shiroFactory.isAcountContainTCJKeShi();
            // 登录账号是否包含任何投促局科室 排除局长
            Boolean isContainAnyTCJKeShiExcludeJZ = shiroFactory.isAcountContainTCJKeShiExcludeJZ();
            // 登录账号科室是否包含 信息中心
            Boolean isAcountContainXXZX = shiroFactory.isAcountContainXXZX();
            // 登录账号是否 只包含 信息中心
            Boolean isAcountOnlyContainXXZX = shiroFactory.isAcountOnlyContainXXZX();
            //登录账号是否是 办公室归档科员、收文发起科员为同一特定账号 收文/归档科员（登录名:swgd）
            Boolean isAccountSWGD = shiroFactory.isAccountSWGD();
            //登录账号是否是 确定办公室科长只为特定的一个账号：办公室主任 (登录名:bgszr)
            Boolean isAccountBGSZR = shiroFactory.isAccountBGSZR();
            if (isContainPingTai){
                roleNameSet.add("-100PT");
            }
            if (isContainInfoCenter){
                roleNameSet.add("-100IC");
            }
            if (isContainOfficeDept){
                roleNameSet.add("-100BGShiKeYuan");
            }
            if (isContainTCJKeYuan){
                roleNameSet.add("-100TCJKeYuan");
            }
            if(isAnyTCJKZoFJZoJZ){
                roleNameSet.add("-100AnyTCJKZoFJZoJZ");
            }
            //超级管理员：
            if(ShiroKit.isAdmin()){
                roleNameSet.add("-100Administrator");
            }
            // 包含局长角色 （包括只包含局长的情况）
            if (ShiroKit.isJuZhang()){
                roleNameSet.add("-100ContainJuZhang");
            }
            // 只包含局长角色
            if (ShiroKit.isOnlyJuZhang()){
                roleNameSet.add("-100OnlyJuZhang");
            }
            // 登录账号包含投任一促局的科室或超级管理员才能查看【OA管理】
            if (isContainTCJKeShi){
                roleNameSet.add("-100ContainAnyTCJKeShi");
            }
            // 登录账号是否包含任何投促局科室 排除局长
            if (isContainAnyTCJKeShiExcludeJZ){
                roleNameSet.add("-100ContainAnyTCJKeShiExcJZ");
            }

            // 登录账号科室是否包含 信息中心
            if (isAcountContainXXZX && !isAcountOnlyContainXXZX){
                roleNameSet.add("-100ContainXXZX");
            }
            // 登录账号是否 只包含 信息中心
            if (isAcountOnlyContainXXZX && !isAcountContainXXZX){
                roleNameSet.add("-100OnlyXXZX");
            }

            //登录账号是否是 办公室归档科员、收文发起科员为同一特定账号 收文/归档科员（登录名:swgd）
            if (isAccountSWGD){
                roleNameSet.add("-100isSWGD");
            }
            //登录账号是否是 确定办公室科长只为特定的一个账号：办公室主任 (登录名:bgszr)
            if (isAccountBGSZR){
                roleNameSet.add("-100isBGSZR");
            }
            //add by lgg end
            String roleName = shiroFactory.findRoleNameByRoleId(roleId);
            roleNameSet.add(roleName);
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionSet);
        info.addRoles(roleNameSet);
        return info;
    }

    /**
     * 设置认证加密方式
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        super.setCredentialsMatcher(md5CredentialsMatcher);
    }
}
