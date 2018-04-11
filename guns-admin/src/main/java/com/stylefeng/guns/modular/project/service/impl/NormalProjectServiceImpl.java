package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.dao.FollowProjectMapper;
import com.stylefeng.guns.common.persistence.dao.NormalProjectMapper;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.project.dao.FollowProjectDao;
import com.stylefeng.guns.modular.project.dao.NormalProjectDao;
import com.stylefeng.guns.modular.project.dao.ProCompanyDao;
import com.stylefeng.guns.modular.project.dao.ProIndustryTypeDao;
import com.stylefeng.guns.modular.project.dto.*;
import com.stylefeng.guns.modular.project.service.INormalProjectService;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.warpper.BaseWarpper;
import com.stylefeng.guns.modular.util.DeptLvUtils;
import com.stylefeng.guns.modular.util.ExcelManage;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 常规项目Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Service
public class NormalProjectServiceImpl extends ServiceImpl<BaseMapper<NormalProject>,NormalProject> implements INormalProjectService {
    @Autowired
    NormalProjectDao normalProjectDao;
    @Autowired
    private UserMgrDao userMgrDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FollowProjectMapper followProjectMapper;
    @Autowired
    private NormalProjectMapper normalProjectMapper;
    @Autowired
    private ProCompanyDao companyDao;
    @Autowired
    private ExcelManage excelManage;

    @Autowired
    private FollowProjectDao followProjectDao;
    @Autowired
    private IRoleService roleService;

    @Autowired
    private ProIndustryTypeDao proIndustryTypeDao;

    @Override
    /**
     * 获取常规项目详情
     * @return
     */
    public NormalProjrctMiniDTO getDetailById(Integer id) {
        NormalProject detail = super.selectById(id);
        NormalProjrctMiniDTO dto = new NormalProjrctMiniDTO();
        if(!StringUtils.isEmpty(detail)){
            BeanUtils.copyProperties(detail,dto);
            Integer CreateUserId = dto.getCreateUserId();
            User user = userMapper.selectById(CreateUserId);
            List<ProCompany> companies = companyDao.getCompaniesByProId(id);
            List<Object> followOffice = normalProjectDao.getFollowOffice(id ,1);
//            List<Integer> followFlat = normalProjectDao.getFollowFlat(id);
            dto.setCompanies(companies);
            dto.setFollowOffice(followOffice);
            //添加创建人
            dto.setCreateUserName(user.getName());
            dto.setCreateAccount(user.getAccount());
//            dto.setFollowFlat(followFlat);
            //添加产业集合
            Map chanyeMap = new HashMap();
            chanyeMap.put("proId", id);
            chanyeMap.put("folType", 1);
            List<Map> chanyeList = proIndustryTypeDao.queryIndustryTypeByProId(chanyeMap);
            dto.setChanyeList(chanyeList);
            return dto;
        }else {
            return null;
        }
    }

    /**
     * 获取分页列表
     * @param map
     * @return
     */
    @Override
    public Page<NormalProjrctInfoDTO> list(Map<String, Object> map) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        Page<NormalProjrctInfoDTO> page = new PageFactory<NormalProjrctInfoDTO>().defaultPage();
        List<Map<String,Object>> normalProjects =  normalProjectDao.list(DataScope.getInstance("createTime",false,map),page,parasMapE);
        page.setRecords((List<NormalProjrctInfoDTO>) new BaseWarpper(normalProjects).warp());
        return page;
    }
    /**
     * 获取人员信息
     * @return
     */
    @Override
    public List<ProFollowsPersonInfoDto> getFollowsPerson(String name,Integer proId) {
     /*   //获取部门表中投促局科室id
        List<Integer> deptIds = normalProjectDao.getIds();
        List<Integer> roleIds = new ArrayList<>();
        List<ProFollowsPersonInfoDto> followsPerson = new ArrayList<>();

        for (Integer id:deptIds){
            List<Integer> rid = normalProjectDao.getRoleIds(id);
            roleIds.addAll(rid);
        }

        for (Integer id:roleIds) {
            List<ProFollowsPersonInfoDto> person = userMgrDao.getFollowsPerson(name,id);
            followsPerson.addAll(person);
        }
        List fps = new ArrayList(new HashSet(followsPerson));
        return fps;*/
        // 通过proId查询到 该项目的所有跟踪科室中 投促局下科室 对应角色中的科员角色id
//        List<Integer> followProjectKeYuanRoleIds = followProjectDao.selectFollowProjectKeYuanRoleIds(proId);
        //获取跟踪科室下所有人员id，包括科长，科员，副局长等等。。。。
        List<Integer> followProjectAllRoleIds = followProjectDao.selectFollowProjectAllRoleIds(proId);
        List<ProFollowsPersonInfoDto> followsPerson = new ArrayList<>();


        for (Integer id:followProjectAllRoleIds) {
            List<ProFollowsPersonInfoDto> person = userMgrDao.getFollowsPerson(name,id);
            followsPerson.addAll(person);
        }
        List fps = new ArrayList(new HashSet(followsPerson));
        return fps;
    }

    /**
     * 获取跟踪平台信息
     * @return
     */
    @Override
    public List<FollowInfoDTO> followsPlat() {
        List<FollowInfoDTO> infos = normalProjectDao.getFollowsPlat();
        return infos;
    }

    /**
     * 获取跟踪科室信息
     * @return
     */
    @Override
    public List<FollowInfoDTO> followsOffice() {
        List<FollowInfoDTO> infos = normalProjectDao.followsOffice();
        return infos;
    }
    /**
     * 获取跟踪街道信息
     * @return
     */
    @Override
    public List<FollowInfoDTO> followsStreet() {
        List<FollowInfoDTO> infos = normalProjectDao.followsStreet();
        return infos;
    }


    @Override
    public int insertProject(Map<Object, Object> map) {
        Integer deptLevel = ShiroKit.getUser().getMaxDeptLevel();
        if (deptLevel > 1) {
            map.put("isBack", 0);
        } else {
            map.put("isBack", 1);
        }
        normalProjectDao.insertProject(map);
        return Integer.parseInt(map.get("newId").toString());
    }

    @Override
    public int updateProject(Map<Object, Object> map) {
//        Map folMap = new HashMap();
//        folMap.put("proId", map.get("id"));
//        folMap.put("userId", map.get("userId"));
//        folMap.put("folType", 1);
//        Integer deptLv = followProjectDao.queryDeptLv(folMap);
//        if (deptLv > 1) {
//            map.put("isBack", 0);
//        } else {
//            map.put("isBack", 1);
//        }
        DeptLvUtils.getInstance().setIsBack(map);
        return normalProjectDao.updateProject(map);
    }

    /**
     * 获取洽谈信息
     * @return
     */
    @Override
    public NormalProTalkInfoDTO getProtalkByProId(Integer id) {
        NormalProject normalProject = normalProjectMapper.selectById(id);
        NormalProTalkInfoDTO dto = new NormalProTalkInfoDTO();
        List<ProAttachment> attachments = normalProjectDao.getAttachByProId(id);
        List<UnitLiable> liables = normalProjectDao.getUnitLiableByProId(id);
        List<ProArea> proAreas = normalProjectDao.getProAreasByProId(id);
        List<ProAdviseOpeType> opeTypes = normalProjectDao.getTypeByProId(id);
        List<ProContractType> contractType = normalProjectDao.getContractTypeByProId(id);
        if (!StringUtils.isEmpty(normalProject)){
            BeanUtils.copyProperties(normalProject,dto);
        }else {
            return null;
        }
        if (!StringUtils.isEmpty(attachments)){
            dto.setAttachments(attachments);
        }
        if (!StringUtils.isEmpty(liables)){
            dto.setUnitLiables(liables);
        }
        if (!StringUtils.isEmpty(proAreas)){
            dto.setProAreas(proAreas);
        }
        if (!StringUtils.isEmpty(opeTypes)){
            dto.setOpeTypes(opeTypes);
        }
        if (!StringUtils.isEmpty(contractType)){
            dto.setContractType(contractType);
        }
        return dto;
    }

    /**
     * 常规项目履约信息
     */
    @Override
    public NormalProConventionInfoDTO getProConventionByProId(Integer id) {
        NormalProject normalProject = normalProjectMapper.selectById(id);
        NormalProConventionInfoDTO dto = new NormalProConventionInfoDTO();
        if (!StringUtils.isEmpty(normalProject)){
            BeanUtils.copyProperties(normalProject,dto);
        }else {
            return null;
        }
        return dto;
    }

    /**
     * 导出excel
     * @return
     */
    @Override
    public List<ExportExcelDTO> exportExcel(List<Integer> deptIds,String conStart,String conEnd,
                                            String talkStartTime,String talkEndTime,String time,String cTime) {
        List<ExportExcelDTO> list ;
        if (ShiroKit.isAdmin()){
            list = normalProjectDao.getExcelListAdmin();
        }else{
            list = normalProjectDao.getExcelList(deptIds);
        }
        for (int i = 0 ; i < list.size() ; i ++){
            Integer talkTime = null;
            Integer conTime = null;
            Integer proid = Integer.valueOf(list.get(i).getId());
            if (!StringUtils.isEmpty(time) && time.trim().contains("7")){
                talkTime = 7;
            }
            if (!StringUtils.isEmpty(time) && time.trim().contains("15")){
                talkTime = 15;
            }
            if (!StringUtils.isEmpty(time) && time.trim().contains("30")){
                talkTime = 30;
            }
            if (!StringUtils.isEmpty(cTime) && cTime.trim().contains("7")){
                conTime = 7;
            }
            if (!StringUtils.isEmpty(cTime) && cTime.trim().contains("15")){
                conTime = 15;
            }
            if (!StringUtils.isEmpty(cTime) && cTime.trim().contains("30")){
                conTime = 30;
            }
            if (!StringUtils.isEmpty(proid)){
                //项目履约情况:1.不正常履约;2.正常履约;
                ExportExcelDTO talkDto = normalProjectDao.getTalkListByTime(proid,talkStartTime,talkEndTime,talkTime);
                ExportExcelDTO conventionDto = normalProjectDao.getConListByTime(proid,conStart,conEnd,conTime);
                if (!StringUtils.isEmpty(talkDto)) {
                list.get(i).setTalkProgress(talkDto.getTalkProgress());
                list.get(i).setTalkQuestion(talkDto.getTalkQuestion());
                list.get(i).setTalkNextStep(talkDto.getTalkNextStep());
                list.get(i).setTalkIsvisit(talkDto.getTalkIsvisit());
                list.get(i).setTalkVisitLv(talkDto.getTalkVisitLv());
                }
                if (!StringUtils.isEmpty(conventionDto) && Integer.valueOf(list.get(i).getNorProConventionType().trim()) == 1) {
                    list.get(i).setConNextAdvise(conventionDto.getConNextAdvise());
                    list.get(i).setConProConventionInfo(conventionDto.getConProConventionInfo());
                }
            }
        }
        return list;
    }

    @Override
    public int updateFolType(Map<String, Object> map) {
        return normalProjectDao.updateFolType(map);
    }

    @Override
    public String getDeptNameById(Integer did) {
       String str = normalProjectDao.getDeptNameById(did);
        return str;
    }

    @Override
    public boolean checkNormalProject(Map<Object, Object> map) {

        Object name = map.get("name");
        if (name != null && name != "null") {
            int count = 0;
            Object proId = map.get("proId");
            if (proId != null && proId != "") {
                count = normalProjectDao.checkNormalProject(name.toString(), Integer.parseInt(proId.toString()));
            } else {
                count = normalProjectDao.checkNormalProject(name.toString(), null);
            }

            if (count > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isAccountUseProjUpdateBtn(Integer normalProjectId) {
//        Boolean reBool = false;
//        NormalProject normalProject = super.selectById(normalProjectId);
//        // 项目跟踪状态:1.待分配;2.已分配;
//        if (ToolUtil.isNotEmpty(normalProject)){
//            if (Constant.NOMALPROJECT_FOLTYPE_DAIFENPEI.equals(normalProject.getFolType())){
//                // 项目跟踪状态: 处于待分配，只有信息中心的人有修改权限的操作，即登录账号拥有信息中心科室即有修改权限
//                if(ShiroKit.isAcountContainXXZX()){
//                    return true;
//                }
//            }else if (Constant.NOMALPROJECT_FOLTYPE_YIFENPEI.equals(normalProject.getFolType())){
//                // 项目跟踪状态: 处于已分配，信息中心的人和跟踪人员信息 有修改权限的操作，即登录账号拥有信息中心科室或是这个项目的跟踪人有修改的权限
//                ShiroUser user = ShiroKit.getUser();
//             /*   if (user != null && normalProject.getFollowUserId() != null){
//                   if (ShiroKit.isAcountContainXXZX() || (int)user.getId() == (int) normalProject.getFollowUserId()){
//                        return true;
//                   }
//                }*/
//                if (user != null && ShiroKit.isAcountContainXXZX()){
//                    return true;
//                }
//                if ( normalProject.getFollowUserId() != null && (int)user.getId() == (int) normalProject.getFollowUserId()){
//                    return true;
//                }
//            }
//
//        }

        //查询所有能操作的投促局科室，包括信息中心
        Map map = new HashMap();
        map.put("proId", normalProjectId);
        map.put("userId", ShiroKit.getUser().id);
        map.put("folType", 1);
        return followProjectDao.checkProAuthByUserId(map) > 0;
    }

    @Override
    public boolean isAccountProjFollowBtn(Integer normalProjectId) {
//        Boolean reBool = false;
//        // 项目跟踪状态:1.待分配;2.已分配;
//        // 状态优先 ： 项目状态必须处于 已分配状态 才可能有 跟踪人员信息按钮，且必须是被分配科室的
//        NormalProject normalProject = super.selectById(normalProjectId);
//        List<Integer> ownroleIds = ShiroKit.getUser().getRoleList();
//        // 信息中心科长角色id
//        Role xxzxKZRole = roleService.getXXZXKZRole();
//        Integer xxzxKZRoleId = null;
//        if (ToolUtil.isNotEmpty(xxzxKZRole)){
//            xxzxKZRoleId = xxzxKZRole.getId();
//        }
//        if (CollectionUtils.isNotEmpty(ownroleIds) && ToolUtil.isNotEmpty(normalProject) &&
//                Constant.NOMALPROJECT_FOLTYPE_YIFENPEI.equals(normalProject.getFolType())){
//            // 1、通过proId查询到 该项目的所有跟踪科室中 投促局下科室 对应角色中的科长角色
//            List<Integer> followProjectKZRoleIds = followProjectDao.selectFollowProjectKZRoleIds(normalProjectId);
//            // 2、获取登录账号所拥有的 投促局下的科室 对应角色中的科长角色
//            List<Integer> accoutOwnTcjKZRoleIds = followProjectDao.selectOwnTcjKZRoleIds(ownroleIds);
//            // 排除掉信息中心的科长
//            followProjectKZRoleIds.remove(xxzxKZRoleId);
//            accoutOwnTcjKZRoleIds.remove(xxzxKZRoleId);
//            // 如果项目投促局科长角色集合 与 登录账号投促局科长角色集合有交集，即有权限
//            // x.retainAll(y); retainAll只会对X做处理
//            if (CollectionUtils.isNotEmpty(followProjectKZRoleIds)){
//                followProjectKZRoleIds.retainAll(accoutOwnTcjKZRoleIds);
//                if (CollectionUtils.isNotEmpty(followProjectKZRoleIds)){// 有交集，则代表有此按钮权限
//                    reBool = true;
//                }
//            }
//
//        }
        Map map = new HashMap();
        map.put("proId", normalProjectId);
        map.put("userId", ShiroKit.getUser().id);
        map.put("folType", 1);
        //过滤街道和平台
        map.put("isAll", 1);
        return followProjectDao.checkProAuthByUserId(map) > 0;
    }
    //分配跟踪人员，更新项目表
    @Override
    public Integer updateNomalProject(Map<String, Object> map) {
        Integer count=normalProjectDao.updateNomalProject(map);
        return count;
    }


//  /*  public static void main(String[] args) {
//        List<Integer> sList = new ArrayList<Integer>();
//        List<Integer> sList2 = new ArrayList<Integer>();
//        sList.add(1);
//        sList.add(2);
//        sList.add(3);
//        sList.add(4);
//
//        sList2.add(1);
//        sList2.add(3);
//        sList2.add(5);
//        sList2.remove(null);
//        sList2.retainAll(sList);
//
//        for (Integer s: sList2){
//            System.out.println(s);
//        }
//
//
//    }*/
}
