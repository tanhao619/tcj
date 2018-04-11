package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.oa.dao.WorkflowDao;
import com.stylefeng.guns.modular.system.dao.RoleDao;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl extends ServiceImpl<BaseMapper<User>,User> implements IUserService {
    @Resource
    RoleDao roleDao;

    @Autowired
    private WorkflowDao workflowDao;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserByNTD(String deptSimplename, String deptTips, String roleDuty) {
        List<Role> NTDRoles = roleDao.getRoleByNTD(deptSimplename, deptTips, roleDuty);
        if (!CollectionUtils.isEmpty(NTDRoles)){
            Integer NTDRoleId = NTDRoles.get(0).getId();
            List<User> NTDUsers = workflowDao.selectOfficeKZs(NTDRoleId);
            if (NTDUsers != null && NTDUsers.size() >0){
                return NTDUsers;
            }
        }
        return null;
    }

    @Override
    public User selectUserByAccount(String account) {
        List<User> accountUsers = userMapper.selectList(new EntityWrapper<User>().eq("account", account));
        if (!CollectionUtils.isEmpty(accountUsers)){
            return accountUsers.get(0);
        }
        return null;
    }

    @Override
    public Boolean isNotOnlyContainAdmin(String[] strRoleIds) {
        Boolean reBool = false;
        try {
            List<String> fpRoleIds = Arrays.asList(strRoleIds);
            if (!CollectionUtils.isEmpty(fpRoleIds)){
                for (String rId : fpRoleIds){
                    Boolean tempBool = ShiroKit.isAdmin(Integer.parseInt(rId));
                    if (tempBool == true && fpRoleIds.size() > 1){
                        reBool = tempBool;
                    }
                }
            }
        }catch (Exception e){
            RecordLogTools.error("分配角色出错！",e);
        }
        return reBool;
    }
}
