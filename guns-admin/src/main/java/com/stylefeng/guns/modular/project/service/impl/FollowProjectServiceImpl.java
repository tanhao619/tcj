package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.FollowProject;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.FollowProjectDao;
import com.stylefeng.guns.modular.project.service.IFollowProjectService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目跟踪Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Service
public class FollowProjectServiceImpl extends ServiceImpl<BaseMapper<FollowProject>,FollowProject> implements IFollowProjectService {

    @Autowired
    private FollowProjectDao followProjectDao;
    private FollowProject followProject = null;

    @Override
    public boolean batchInsertFollowProjects(int proId, Map map) {
        int t_deptCount = 0;
        Object deptCount = map.get("deptCount");
        Object folType = map.get("folType");
        FollowProject followProject = null;
        List<FollowProject> followProjects = new ArrayList<FollowProject>();
        List<Integer> deptIds = new ArrayList<>();
        if (deptCount == null || deptCount == "") {
            //提交时加上信息中心 ，“更改跟踪科室”按钮触发
            if (folType != null && folType != "null" && Integer.parseInt(folType.toString()) > 1) {
                Integer infoCenterId = ShiroKit.getUser().getInfoCenterId();
                followProject = setFollowProject(map, proId);
                followProject.setDeptId(infoCenterId);
                followProjects.add(followProject);
            }
            setFollowProjects(folType ,map, proId, followProjects);

            if (followProjects.size() > 0) {
                long size = followProjectDao.batchInsert(followProjects);
                if (size > 0) {
                    return true;
                }
            }
            //return false;
        }else {
            t_deptCount = Integer.parseInt(deptCount.toString());
            for (int i = 0; i < t_deptCount; i ++ ) {
                followProject = setFollowProject(map, proId);
                followProject.setDeptId(Integer.parseInt(ParamsUtils.getMap("followDeptId" + i, map)));
                if (StringUtils.isNotBlank(ParamsUtils.getMap("deptId" + i, map))) {
                    int deptId = Integer.parseInt(map.get("deptId" + i).toString());
                    followProject.setId(deptId);
                    deptIds.add(deptId);
                    continue;
                }
                followProjects.add(followProject);
            }
        }
        
        setFollowProjects(folType ,map, proId, followProjects);

        if (deptIds.size() > 0 || followProjects.size() > 0) {
            followProjectDao.updateCurrentTimeByProId(deptIds, proId, ParamsUtils.getMap("currentTime", map),ShiroKit.getUser().id,ShiroKit.getUser().name);
            followProjectDao.deleteFollowProjectByProId(deptIds, proId, Integer.parseInt(ParamsUtils.getMap("from", map)));
        }
        if (followProjects.size() > 0) {
            long size = followProjectDao.batchInsert(followProjects);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer queryDeptLv(Integer proId, Integer folType) {
        Map map = new HashMap();
        map.put("proId", proId);
        map.put("userId", ShiroKit.getUser().id);
        map.put("folType",folType);
        return followProjectDao.queryDeptLv(map);
    }

    public void setFollowProjects(Object folType, Map map, int proId, List followProjects) {
        //项目新建
        if (folType != null && folType != "null") {
            Object isUpdate = map.get("isUpdate");
            if (isUpdate != null && !Boolean.parseBoolean(isUpdate.toString())) {
                List<Integer> depts = ShiroKit.getUser().getDeptIds();
                for (int k = 0; k < depts.size(); k ++) {
                    followProject = setFollowProject(map, proId);
                    followProject.setDeptId(depts.get(k));
                    followProjects.add(followProject);
                }
            } else {
                return;
            }
            // ==1 新建状态
            if (Integer.parseInt(folType.toString()) == 1) {
                Integer infoCenterId = ShiroKit.getUser().getInfoCenterId();
                followProject = setFollowProject(map, proId);
                followProject.setDeptId(infoCenterId);
                followProjects.add(followProject);
            }
        }
    }

    private  FollowProject setFollowProject(Map map, int proId) {
        ShiroUser user = ShiroKit.getUser();
        followProject = new FollowProject();
        followProject.setProId(proId);
        followProject.setUserId(user.id);
        followProject.setUserName(user.name);
        followProject.setFolType(Integer.parseInt(ParamsUtils.getMap("from", map)));
        followProject.setCurrentTime(ParamsUtils.getMap("currentTime", map));
        return followProject;
    }

}
