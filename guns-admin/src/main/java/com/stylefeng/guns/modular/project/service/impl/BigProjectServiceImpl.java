package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.dao.BigProjectMapper;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.BigProject;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.project.dao.BigProjectDao;
import com.stylefeng.guns.modular.project.dao.FollowProjectDao;
import com.stylefeng.guns.modular.project.dao.ProIndustryTypeDao;
import com.stylefeng.guns.modular.project.dto.BigExcelExportDTO;
import com.stylefeng.guns.modular.project.dto.BigProDetailDTO;
import com.stylefeng.guns.modular.project.dto.BigProjrctInfoDTO;
import com.stylefeng.guns.modular.project.service.IBigProjectService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.warpper.BaseWarpper;
import com.stylefeng.guns.modular.util.DeptLvUtils;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 重大项目Service
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
@Service
public class BigProjectServiceImpl extends ServiceImpl<BaseMapper<BigProject>,BigProject> implements IBigProjectService {

    @Autowired
    private BigProjectDao bigProjectDao;
    @Autowired
    private BigProjectMapper bigProjectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private FollowProjectDao followProjectDao;

    @Autowired
    private ProIndustryTypeDao proIndustryTypeDao;

    @Override
    public int insertProject(Map<Object, Object> map) {
        Integer deptLevel = ShiroKit.getUser().getMaxDeptLevel();
        if (deptLevel > 1) {
            map.put("isBack", 0);
        } else {
            map.put("isBack", 1);
        }
        bigProjectDao.insertProject(map);
        return Integer.parseInt(map.get("newId").toString());
    }

    @Override
    public int updateProject(Map<Object, Object> map) {
        DeptLvUtils.getInstance().setIsBack(map);
        return bigProjectDao.updateProject(map);
    }

    /**
     * 重大项目excel导出
     */
    @Override
    public List<BigExcelExportDTO> exportExcel(List<Integer> deptIds, String startTime, String endTime, String workProcessTime) {
        List<BigExcelExportDTO> list ;
        if (ShiroKit.isAdmin()){
            list = bigProjectDao.getBigProExcelListAdmin();
        }else{
            list = bigProjectDao.getBigProExcelList(deptIds);
        }
        for (int i = 0 ; i < list.size() ; i ++) {
            Integer time = null;
            Integer proid = Integer.valueOf(list.get(i).getId());
            if (!StringUtils.isEmpty(workProcessTime) && workProcessTime.trim().contains("7")) {
                time = 7;
            }
            if (!StringUtils.isEmpty(workProcessTime) && workProcessTime.trim().contains("15")) {
                time = 15;
            }
            if (!StringUtils.isEmpty(workProcessTime) && workProcessTime.trim().contains("30")) {
                time = 30;
            }
            if (!org.springframework.util.StringUtils.isEmpty(proid)){
                String workProcess = bigProjectDao.getWorkProcessListByTime(proid,startTime,endTime,time);
                if (!org.springframework.util.StringUtils.isEmpty(workProcess)) {
                    list.get(i).setWorkProcess(workProcess);
                }
            }

        }
        return list;
    }

    /**
     * 获取项目详情
     */
    @Override
    public BigProDetailDTO getDetailById(Integer id) {
        BigProDetailDTO dto = new BigProDetailDTO();
        BigProject project = bigProjectMapper.selectById(id);
        if (org.springframework.util.StringUtils.isEmpty(project)){
            return null;
        }
        List<Map> proCategary = bigProjectDao.getIndustryTypeByProId(id);
        List<Map> packPosition = bigProjectDao.getPackPositionByProId(id);
        List<Map> uniliable = bigProjectDao.getUniliableByProId(id);
        List<Map> adviseOpeType = bigProjectDao.getAdviseOpeTypeByProId(id);
        List<Map> followPlat = bigProjectDao.getFollowPlatByProId(id);
        List<Map> buildPlace = bigProjectDao.getBuildPlaceByProId(id);
        User user = userMapper.selectById(project.getCreateUserId());
        BeanUtils.copyProperties(project,dto);
        dto.setCreateUserName(user.getName());
        dto.setCreateAccount(user.getAccount());
        dto.setIndustryType(proCategary);
        dto.setPackPosition(packPosition);
        dto.setUniliable(uniliable);
        dto.setAdviseOpeType(adviseOpeType);
        dto.setFollowPlat(followPlat);
        dto.setBuildPlace(buildPlace);

        //添加产业集合
        Map chanyeMap = new HashMap();
        chanyeMap.put("proId", id);
        chanyeMap.put("folType", 2);
        List<Map> chanyeList = proIndustryTypeDao.queryIndustryTypeByProId(chanyeMap);
        dto.setChanyeList(chanyeList);
        return dto;
    }

    @Override
    public int updateFolType(Map<String, Object> map) {
        return bigProjectDao.updateFolType(map);
    }

    @Override
    public boolean isAccountUseProjUpdateBtn(Integer bigProjectId) {
        //查询所有能操作的投促局科室，包括信息中心
        Map map = new HashMap();
        map.put("proId", bigProjectId);
        map.put("userId", ShiroKit.getUser().id);
        map.put("folType", 2);
        return followProjectDao.checkProAuthByUserId(map) > 0;
    }

    @Override
    public boolean isAccountProjFollowBtn(Integer bigProjectId) {
        Map map = new HashMap();
        map.put("proId", bigProjectId);
        map.put("userId", ShiroKit.getUser().id);
        map.put("folType", 2);
        //过滤街道和平台
        map.put("isAll", 1);
        return followProjectDao.checkProAuthByUserId(map) > 0;
    }
    //分配跟踪人员，更新项目表
    @Override
    public Integer updateBigProject(Map<String, Object> map) {
        Integer count=bigProjectDao.updateBigProject(map);
        return count;
    }

    @Override
    public boolean checkBigProject(Map<Object, Object> map) {

        Object name = map.get("name");
        if (name != null && name != "null") {
            int count = 0;
            Object proId = map.get("proId");
            if (proId != null && proId != "") {
                count = bigProjectDao.checkBigProject(name.toString(), Integer.parseInt(proId.toString()));
            }else {
                count = bigProjectDao.checkBigProject(name.toString(), null);
            }
            if (count > 0) {
                return true;
            }
        }

        return false;
    }


    /**
     * 获取分页列表
     * @param map 搜索参数
     * @return
     */
    @Override
    public Page<BigProjrctInfoDTO> list(Map<String, Object> map) {
        // 处理传入的策划包装定位参数（将String形如 "1,2,3" 转换成数组）
        String packPosition = (String) map.get("packPosition");
        if (StringUtils.isNotBlank(packPosition)) {
            String[] split = packPosition.split(",");
            map.remove("packPosition");
            map.put("packPosition", split);
        }

        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        Page<BigProjrctInfoDTO> page = new PageFactory<BigProjrctInfoDTO>().defaultPage();
        List<Map<String,Object>> normalProjects =  bigProjectDao.list(DataScope.getInstance("createTime",false,map),page,parasMapE);
        page.setRecords((List<BigProjrctInfoDTO>) new BaseWarpper(normalProjects).warp());
        return page;
    }
}
