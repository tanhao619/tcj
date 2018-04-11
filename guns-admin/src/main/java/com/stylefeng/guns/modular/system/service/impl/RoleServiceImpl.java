package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.RoleDao;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.common.persistence.dao.RelationMapper;
import com.stylefeng.guns.common.persistence.dao.RoleMapper;
import com.stylefeng.guns.common.persistence.model.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<BaseMapper<Role>,Role> implements IRoleService {

    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleDao roleDao;

    @Resource
    RelationMapper relationMapper;
    
    @Autowired
    private IDeptService deptService;
    @Override
    @Transactional(readOnly = false)
    public void setAuthority(Integer roleId, String ids) {

        // 删除该角色所有的权限
        this.roleDao.deleteRolesById(roleId);

        // 添加新的权限
        for (Integer id : Convert.toIntArray(ids)) {
            Relation relation = new Relation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delRoleById(Integer roleId) {
        //删除角色
        this.roleMapper.deleteById(roleId);

        // 删除该角色所有的权限
        this.roleDao.deleteRolesById(roleId);
    }

    /**
     * 通过deptid 与duty 查询角色
     * @param deptid
     * @param duty
     * @return
     */
    @Override
    public List<Role> selectRoleByDidADuty(Integer deptid, String duty) {
        return roleDao.selectRoleByDidADuty(deptid, duty);
    }

    @Override
    public Role getXXZXKZRole() {
        Dept xxzxDept = deptService.getXXZXDept();
        if (ToolUtil.isNotEmpty(xxzxDept)){
            List<Role> roles = roleDao.selectRoleByDidADuty(xxzxDept.getId(), "2");
            if (!CollectionUtils.isEmpty(roles)){
                return roles.get(0);
            }
        }
        return null;
    }

}
