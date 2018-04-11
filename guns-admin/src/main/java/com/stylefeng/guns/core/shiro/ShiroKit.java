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
package com.stylefeng.guns.core.shiro;

import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.util.SpringContextUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * shiro工具类
 *
 * @author dafei, Chill Zhuang
 */
public class ShiroKit {

    private static final String NAMES_DELIMETER = ",";

    // add by lgg
    private static IRoleService roleService = SpringContextUtil.getBean(IRoleService.class);

    private static UserMapper  userMapper = SpringContextUtil.getBean(UserMapper.class);

    private static IDeptService  deptService = SpringContextUtil.getBean(IDeptService.class);
    /**
     * 加盐参数
     */
    public final static String hashAlgorithmName = "MD5";

    /**
     * 循环次数
     */
    public final static int hashIterations = 1024;

    /**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @param saltSource 密码盐
     * @return
     */
    public static String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("monkey","monkey"));
        System.out.println(md5("monkey1","monkey1"));
        System.out.println(md5("monkey2","monkey2"));
        System.out.println(md5("monkey3","monkey3"));
    }
    /**
     * 获取随机盐值
     * @param length
     * @return
     */
    public static String getRandomSalt(int length) {
        return ToolUtil.getRandomString(length);
    }

    /**
     * 获取当前 Subject
     *
     * @return Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取封装的 ShiroUser
     *
     * @return ShiroUser
     */
    public static ShiroUser getUser() {
        if (isGuest()) {
            return null;
        } else {
            return (ShiroUser) getSubject().getPrincipals().getPrimaryPrincipal();
        }
    }

    /**
     * 从shiro获取session
     *
     */
    public static Session getSession() {
        return getSubject().getSession();
    }

    /**
     * 获取shiro指定的sessionKey
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttr(String key) {
        Session session = getSession();
        return session != null ? (T) session.getAttribute(key) : null;
    }

    /**
     * 设置shiro指定的sessionKey
     *
     */
    public static void setSessionAttr(String key, Object value) {
        Session session = getSession();
        session.setAttribute(key, value);
    }

    /**
     * 移除shiro指定的sessionKey
     */
    public static void removeSessionAttr(String key) {
        Session session = getSession();
        if (session != null)
            session.removeAttribute(key);
    }

    /**
     * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
     *
     * @param roleName
     *            角色名
     * @return 属于该角色：true，否则false
     */
    public static boolean hasRole(String roleName) {
        return getSubject() != null && roleName != null
                && roleName.length() > 0 && getSubject().hasRole(roleName);
    }

    /**
     * 与hasRole标签逻辑相反，当用户不属于该角色时验证通过。
     *
     * @param roleName
     *            角色名
     * @return 不属于该角色：true，否则false
     */
    public static boolean lacksRole(String roleName) {
        return !hasRole(roleName);
    }

    /**
     * 验证当前用户是否属于以下任意一个角色。
     *
     * @param roleNames
     *            角色列表
     * @return 属于:true,否则false
     */
    public static boolean hasAnyRoles(String roleNames) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();
        if (subject != null && roleNames != null && roleNames.length() > 0) {
            for (String role : roleNames.split(NAMES_DELIMETER)) {
                if (subject.hasRole(role.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }
        return hasAnyRole;
    }

    /**
     * 验证当前用户是否属于以下所有角色。
     *
     * @param roleNames
     *            角色列表
     * @return 属于:true,否则false
     */
    public static boolean hasAllRoles(String roleNames) {
        boolean hasAllRole = true;
        Subject subject = getSubject();
        if (subject != null && roleNames != null && roleNames.length() > 0) {
            for (String role : roleNames.split(NAMES_DELIMETER)) {
                if (!subject.hasRole(role.trim())) {
                    hasAllRole = false;
                    break;
                }
            }
        }
        return hasAllRole;
    }

    /**
     * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
     *
     * @param permission
     *            权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean hasPermission(String permission) {
        return getSubject() != null && permission != null
                && permission.length() > 0
                && getSubject().isPermitted(permission);
    }

    /**
     * 与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过。
     *
     * @param permission
     *            权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean lacksPermission(String permission) {
        return !hasPermission(permission);
    }

    /**
     * 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。与notAuthenticated搭配使用
     *
     * @return 通过身份验证：true，否则false
     */
    public static boolean isAuthenticated() {
        return getSubject() != null && getSubject().isAuthenticated();
    }

    /**
     * 未认证通过用户，与authenticated标签相对应。与guest标签的区别是，该标签包含已记住用户。。
     *
     * @return 没有通过身份验证：true，否则false
     */
    public static boolean notAuthenticated() {
        return !isAuthenticated();
    }

    /**
     * 认证通过或已记住的用户。与guset搭配使用。
     *
     * @return 用户：true，否则 false
     */
    public static boolean isUser() {
        return getSubject() != null && getSubject().getPrincipal() != null;
    }

    /**
     * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
     *
     * @return 访客：true，否则false
     */
    public static boolean isGuest() {
        return !isUser();
    }

    /**
     * 输出当前用户信息，通常为登录帐号信息。
     *
     * @return 当前用户信息
     */
    public static String principal() {
        if (getSubject() != null) {
            Object principal = getSubject().getPrincipal();
            return principal.toString();
        }
        return "";
    }

    /**
     * 获取当前用户的部门数据范围的集合
     */
    public static List<Integer> getDeptDataScope() {
        Integer deptId = getUser().getDeptId();
        List<Integer> subDeptIds = ConstantFactory.me().getSubDeptId(deptId);
        subDeptIds.add(deptId);
        return subDeptIds;
    }

    /**
     * 判断当前用户是否是超级管理员
     * tips = 'administrator' 并且 isSystem='1' 并且 duty='-1'
     */
    public static boolean isAdmin() {
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        for (Integer integer : roleList) {
            String singleRoleTip = ConstantFactory.me().getSingleRoleTip(integer);
            // update by lgg
            Role role = roleService.selectById(integer);
            if (ToolUtil.isNotEmpty(role) && Const.IS_SYSTEM.equals(role.getIsSystem())){
                if (singleRoleTip.equals(Const.ADMIN_NAME) && Const.ADMIN_DUYT.equals(role.getDuty())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据roleId 判断用户是否是超级管理员
     * tips = 'administrator' 并且 isSystem='1' 并且 duty='-1'
     */
    public static boolean isAdmin(Integer roleId) {
            String singleRoleTip = ConstantFactory.me().getSingleRoleTip(roleId);
            // update by lgg
            Role role = roleService.selectById(roleId);
            if (ToolUtil.isNotEmpty(role) && Const.IS_SYSTEM.equals(role.getIsSystem())){
                if (singleRoleTip.equals(Const.ADMIN_NAME) && Const.ADMIN_DUYT.equals(role.getDuty())) {
                    return true;
                }
            }
        return false;
    }

    /**
     * 根据userId判断用户是否是超级管理员
     * tips = 'administrator' 并且 isSystem='1' 并且 duty='-1'
     */
    public static boolean isAdminByUid(Integer userId) {
        User user = userMapper.selectById(userId);
        if (ToolUtil.isNotEmpty(user)){
            Integer[] roleArray = Convert.toIntArray(user.getRoleid());// 角色集合
            for (int roleId : roleArray) {
                // update by lgg start
                // 加入role的时候先查询该role是否还存在
                Role role = roleService.selectById(roleId);
                if (role != null){
                    String singleRoleTip = ConstantFactory.me().getSingleRoleTip(roleId);
                    if (ToolUtil.isNotEmpty(role) && Const.IS_SYSTEM.equals(role.getIsSystem())){
                        if (singleRoleTip.equals(Const.ADMIN_NAME) && Const.ADMIN_DUYT.equals(role.getDuty())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * 判断当前用户是否是局长
     * tips = 'administrator' 并且 isSystem='1' 并且 duty='4'
     */
    public static boolean isJuZhang() {
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        for (Integer integer : roleList) {
            Role role = roleService.selectById(integer);
            String singleRoleTip = ConstantFactory.me().getSingleRoleTip(integer);
            if (ToolUtil.isNotEmpty(role) && Const.IS_SYSTEM.equals(role.getIsSystem())){
                if (singleRoleTip.equals(Const.ADMIN_NAME) && Const.JUZHANG_DUYT.equals(role.getDuty())) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断当前用户是只有局长角色
     * tips = 'administrator' 并且 isSystem='1' 并且 duty='4'
     */
    public static boolean isOnlyJuZhang() {
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (CollectionUtils.isNotEmpty(roleList)&& roleList.size() == 1){
            Integer juzhangRoleId = roleList.get(0);
            Role role = roleService.selectById(juzhangRoleId);
            String singleRoleTip = ConstantFactory.me().getSingleRoleTip(juzhangRoleId);
            if (ToolUtil.isNotEmpty(role) && Const.IS_SYSTEM.equals(role.getIsSystem())){
                if (singleRoleTip.equals(Const.ADMIN_NAME) && Const.JUZHANG_DUYT.equals(role.getDuty())) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 根据roleId 判断当前用户是否是局长
     * tips = 'administrator' 并且 isSystem='1' 并且 duty='4'
     */
    public static boolean isJuZhang(Integer roleId) {
        String singleRoleTip = ConstantFactory.me().getSingleRoleTip(roleId);
        // update by lgg
        Role role = roleService.selectById(roleId);
        if (ToolUtil.isNotEmpty(role) && Const.IS_SYSTEM.equals(role.getIsSystem())){
            if (singleRoleTip.equals(Const.ADMIN_NAME) && Const.JUZHANG_DUYT.equals(role.getDuty())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前用户是否是  收文/归档科员（登录名:swgd）
     *
     */
    public static boolean isSWGD() {
        Boolean reBool = false;
        ShiroUser user = ShiroKit.getUser();
        if (ToolUtil.isNotEmpty(user) && Constant.SWGD.equals(user.getAccount())){
            reBool = true;
        }
        return reBool;
    }

    public static boolean isSWGD(Integer userId) {
        Boolean reBool = false;
        User user = userMapper.selectById(userId);
        if (ToolUtil.isNotEmpty(user) && Constant.SWGD.equals(user.getAccount())){
            reBool = true;
        }
        return reBool;
    }

    /**
     * 判断当前用户是否是 办公室主任 (登录名:bgszr)
     *
     */
    public static boolean isBGSZR() {
        Boolean reBool = false;
        ShiroUser user = ShiroKit.getUser();
        if (ToolUtil.isNotEmpty(user) && Constant.BGSZR.equals(user.getAccount())){
            reBool = true;
        }
        return reBool;
    }

    public static boolean isBGSZR(Integer userId) {
        Boolean reBool = false;
        User user = userMapper.selectById(userId);
        if (ToolUtil.isNotEmpty(user) && Constant.BGSZR.equals(user.getAccount())){
            reBool = true;
        }
        return reBool;
    }


    // 登录账号是否包含信息中心
    public static Boolean isAcountContainXXZX() {
        Boolean reBool = false;
        List<Integer> ownDeptIds = ShiroKit.getUser().getDeptIds();
        Dept xxzxDept = deptService.getXXZXDept();
        if (CollectionUtils.isNotEmpty(ownDeptIds)  && ToolUtil.isNotEmpty(xxzxDept)){
            if (ownDeptIds.contains(xxzxDept.getId())){
                reBool = true;
            }
        }
        return reBool;
    }

}
