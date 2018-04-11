package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 角色相关的dao
 *
 * @author fengshuonan
 * @date 2017年2月12日 下午8:43:52
 */
@Component
public interface RoleDao {

    /**
     * 根据条件查询角色列表
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectRoles(@Param("condition") String condition);

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     * @date 2017年2月13日 下午7:57:51
     */
    int deleteRolesById(@Param("roleId") Integer roleId);

    /**
     * 获取角色列表树
     *
     * @return
     * @date 2017年2月18日 上午10:32:04
     */
    List<ZTreeNode> roleTreeList();

    /**
     * 获取角色列表树
     *
     * @return
     * @date 2017年2月18日 上午10:32:04
     */
    List<ZTreeNode> roleTreeListByRoleId(String[] roleId);

    /**
     *通过科室 simplename、tips 角色duty 确定角色
     */
    List<Role> getRoleByNTD(@Param("simplename")String deptSimplename, @Param("tips")String deptTips,@Param("duty")String roleDuty);

    /**
     *通过科室tips 角色duty 确定角色ids
     */
    List<Integer> getRoleByTipsDuty(@Param("tips")String deptTips,@Param("duty")String roleDuty);
    /**
     * 通过deptid 与duty 查询角色
     * @param deptid
     * @param duty
     * @return
     */
    List<Role> selectRoleByDidADuty(@Param("deptid")Integer deptid, @Param("duty")String duty);
}
