package com.stylefeng.guns.core.shiro.factory;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.state.ManagerStatus;
import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.common.persistence.model.FollowProject;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.oa.dao.WorkflowDao;
import com.stylefeng.guns.modular.project.dao.FollowProjectDao;
import com.stylefeng.guns.modular.project.service.IFollowProjectService;
import com.stylefeng.guns.modular.project.service.INormalProjectService;
import com.stylefeng.guns.modular.system.dao.DeptDao;
import com.stylefeng.guns.modular.system.dao.MenuDao;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactroy implements IShiro {

    @Autowired
    private UserMgrDao userMgrDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private FollowProjectDao followProjectDao;
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IFollowProjectService followProjectService;
    @Autowired
    private INormalProjectService normalProjectService;

    @Autowired
    private WorkflowDao workflowDao;

    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    @Override
    public User user(String account) {

        User user = userMgrDao.getByAccount(account);

        // 账号不存在
        if (null == user) {
            throw new CredentialsException();
        }
        // 账号被冻结
        if (user.getStatus() != ManagerStatus.OK.getCode()) {
            throw new LockedAccountException();
        }
        return user;
    }

    @Override
    public ShiroUser shiroUser(User user) {
        ShiroUser shiroUser = new ShiroUser();

        shiroUser.setId(user.getId());            // 账号id
        shiroUser.setAccount(user.getAccount());// 账号
        shiroUser.setDeptId(user.getDeptid());    // 部门id
        shiroUser.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));// 部门名称
        // add by lgg --start
        // 账号对应的所有所属部门ids
        List<Dept> depts = deptDao.selectDeptsByUserId(user.getId());
        List<Integer> deptIds = depts.stream().map(p -> p.getId()).collect(Collectors.toList());
        shiroUser.setDeptIds(deptIds);
 /*       if (CollectionUtils.isNotEmpty(depts)){
            deptIds = depts.stream().map(p -> p.getId()).collect(Collectors.toList());
            shiroUser.setDeptIds(deptIds);
        }else {// 处理 登录用户对应单位为null的情况
            deptIds.add(-1);
            shiroUser.setDeptIds(deptIds);
        }*/

        // 登录账号能访问的所有项目ids
        List<FollowProject> ownProject = followProjectService.selectList(new EntityWrapper<FollowProject>().in(true, "deptId", deptIds));
        List<Integer> ownProIds = ownProject.stream().filter(p->p.getProId()!=null && normalProjectService.selectById(p.getProId()) != null).
                map(p -> p.getProId()).collect(Collectors.toList());
        shiroUser.setProIds(ownProIds);
        // 登录人最大单位等级 1.郫都区投促局  2.平台工作组 3.街道或其他科室
        Integer maxDeptLevel = deptDao.selectMaxPermissByUserId(user.getId());
        shiroUser.setMaxDeptLevel(maxDeptLevel);
        // 信息中心id
        Integer infoCenterId =  followProjectDao.queryICDeptId();
        shiroUser.setInfoCenterId(infoCenterId);
        
         // 登录账号科室是否包含 信息中心
        Boolean isAcountContainXXZX = isAcountContainXXZX(deptIds);
        shiroUser.setAcountContainXXZX(isAcountContainXXZX);

        // 登录账号是否是 办公室归档科员、收文发起科员为同一特定账号 收文/归档科员（登录名:swgd）
        shiroUser.setAccountisSWGD(isAccountSWGD(user));
        //登录账号是否是 确定办公室科长只为特定的一个账号：办公室主任 (登录名:bgszr)
        shiroUser.setAccountisBGSZR(isAccountBGSZR(user));
        // add by lgg --end
        shiroUser.setName(user.getName());        // 用户名称

        Integer[] roleArray = Convert.toIntArray(user.getRoleid());// 角色集合
        List<Integer> roleList = new ArrayList<Integer>();
        List<String> roleNameList = new ArrayList<String>();
        for (int roleId : roleArray) {
            // update by lgg start
            // 加入role的时候先查询该role是否还存在
            Role role = roleService.selectById(roleId);
            if (role != null){
                roleList.add(roleId);
                roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
            }
            // update by lgg end
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);

        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(Integer roleId) {
        List<String> resUrls = menuDao.getResUrlsByRoleId(roleId);
        return resUrls;
    }

    @Override
    public String findRoleNameByRoleId(Integer roleId) {
        return ConstantFactory.me().getSingleRoleTip(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

    // add by lgg
    // 登录人是否包含平台的单位
    public Boolean isAcountContainPingTai(){
        Boolean reBool = false;
        Integer uid = ShiroKit.getUser().getId();
        List<Dept> depts = deptDao.selectDeptsByUserId(uid);
        long count = depts.stream().filter(p -> StringUtils.hasText(p.getTips()) && "2".equals(p.getTips())).count();
        if (count > 0){
            reBool = true;
        }
        return reBool;
    }
    // 登录人是否包含投促局信息中心的单位
    public Boolean isAcountContainInfoCenter(){
        Boolean reBool = false;
        Integer uid = ShiroKit.getUser().getId();
        List<Dept> depts = deptDao.selectDeptsByUserId(uid);
        long count = depts.stream().filter(p -> StringUtils.hasText(p.getSimplename())
                && Constant.INFO_CENTER.equals(p.getSimplename()) &&
                "1".equals(p.getTips())).count();
        if (count > 0){
            reBool = true;
        }
        return reBool;
    }

    // 登录账号是否包含【办公室科员】的角色
    @Override
    public Boolean isAcountContainOfficeDept() {
        Boolean reBool = false;
        Integer uid = ShiroKit.getUser().getId();
        List<Role> ownRoles = workflowDao.getFaqiRole(uid);
       // 查到"办公室"的dept id
        List<Dept> officeDepts = deptService.selectList(new EntityWrapper<Dept>().eq("simplename", Constant.DEPT_OFFICE)
                .and().eq("tips", "1"));
        if (CollectionUtils.isNotEmpty(officeDepts)){
            long count = ownRoles.stream().filter(p -> Constant.ROLE_KEYUAN.equals(p.getDuty())
                    && p.getDeptid() == officeDepts.get(0).getId()).count();
            if (count > 0){
                reBool = true;
            }

        }
        return reBool;
    }

    // 登录账号是否包含任何投促局【科员】的角色
    @Override
    public Boolean isAcountContainTCJKeYuan() {
        Boolean reBool = false;
        Integer uid = ShiroKit.getUser().getId();
        List<Role> ownRoles = workflowDao.getFaqiRole(uid);
        long count = ownRoles.stream().filter(p -> Constant.ROLE_KEYUAN.equals(p.getDuty())).count();
        if (count > 0){
            reBool = true;
        }
        return reBool;
    }

    // 登录账号是否包含任何投促局【科长/副局长/局长】的角色 用于是否拥有 审批页面的判断
    @Override
    public Boolean isTCJKZoFJZoJZ() {
        Boolean reBool = false;
        Integer uid = ShiroKit.getUser().getId();
        List<Role> ownRoles = workflowDao.getFaqiRole(uid);
        long count = ownRoles.stream().filter(p -> !Constant.ROLE_KEYUAN.equals(p.getDuty())).count();
        if (count > 0){
            reBool = true;
        }
        return reBool;
    }

    // 登录账号是否包含任何投促局科室
    @Override
    public Boolean isAcountContainTCJKeShi() {
        Boolean reBool = false;
        Integer uid = ShiroKit.getUser().getId();
        List<Dept> ownDepts = deptDao.selectDeptsByUserId(uid);
        long count = ownDepts.stream().filter(p -> StringUtils.hasText(p.getTips()) && "1".equals(p.getTips())).count();
        if (count > 0){
            reBool = true;
        }
        return reBool;
    }
    // 登录账号是否包含任何投促局科室 排除局长
    @Override
    public Boolean isAcountContainTCJKeShiExcludeJZ() {
        Boolean reBool = false;
        Integer uid = ShiroKit.getUser().getId();
        List<Dept> ownDepts = deptDao.selectDeptsByUserId(uid);
        long count = ownDepts.stream().filter(p -> StringUtils.hasText(p.getTips()) && "1".equals(p.getTips())
                && !Constant.DEPT_JUZHANGBGS.equals(p.getSimplename())).count();
        if (count > 0){
            reBool = true;
        }
        return reBool;
    }

    @Override
    public Boolean isAcountContainXXZX() {
        Boolean reBool = false;
        List<Integer> ownDeptIds = ShiroKit.getUser().getDeptIds();
        Dept xxzxDept = deptService.getXXZXDept();
        if (CollectionUtils.isNotEmpty(ownDeptIds) && ownDeptIds.size() > 1 && ToolUtil.isNotEmpty(xxzxDept)){
            if (ownDeptIds.contains(xxzxDept.getId())){
                reBool = true;
            }
        }
        return reBool;
    }

    @Override
    public Boolean isAcountOnlyContainXXZX() {
        Boolean reBool = false;
        List<Integer> ownDeptIds = ShiroKit.getUser().getDeptIds();
        Dept xxzxDept = deptService.getXXZXDept();
        if (CollectionUtils.isNotEmpty(ownDeptIds) && ToolUtil.isNotEmpty(xxzxDept)){
            if (ownDeptIds.size() == 1 && (int)ownDeptIds.get(0) == (int)xxzxDept.getId()){
                reBool = true;
            }
        }
        return reBool;
    }

    //登录账号是否是 办公室归档科员、收文发起科员为同一特定账号 收文/归档科员（登录名:swgd）
    @Override
    public Boolean isAccountSWGD() {
        Boolean reBool = false;
        ShiroUser user = ShiroKit.getUser();
        if (ToolUtil.isNotEmpty(user) && Constant.SWGD.equals(user.getAccount())){
            reBool = true;
        }
        return reBool;
    }
    public Boolean isAccountSWGD(User user) {
        Boolean reBool = false;
        if (ToolUtil.isNotEmpty(user) && Constant.SWGD.equals(user.getAccount())){
            reBool = true;
        }
        return reBool;
    }

    //登录账号是否是 确定办公室科长只为特定的一个账号：办公室主任 (登录名:bgszr)
    @Override
    public Boolean isAccountBGSZR() {
        Boolean reBool = false;
        ShiroUser user = ShiroKit.getUser();
        if (ToolUtil.isNotEmpty(user) && Constant.BGSZR.equals(user.getAccount())){
            reBool = true;
        }
        return reBool;
    }
    public Boolean isAccountBGSZR(User user) {
        Boolean reBool = false;
        if (ToolUtil.isNotEmpty(user) && Constant.BGSZR.equals(user.getAccount())){
            reBool = true;
        }
        return reBool;
    }

    private Boolean isAcountContainXXZX( List<Integer> ownDeptIds){
        Boolean reBool = false;
        Dept xxzxDept = deptService.getXXZXDept();
        if (CollectionUtils.isNotEmpty(ownDeptIds)  && ToolUtil.isNotEmpty(xxzxDept)){
            if (ownDeptIds.contains(xxzxDept.getId())){
                reBool = true;
            }
        }
        return reBool;
    }
}
