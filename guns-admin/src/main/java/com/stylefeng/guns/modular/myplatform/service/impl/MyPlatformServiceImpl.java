package com.stylefeng.guns.modular.myplatform.service.impl;


import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.core.shiro.ShiroKit;

import com.stylefeng.guns.modular.myplatform.dao.MyPlatformDao;
import com.stylefeng.guns.modular.myplatform.dto.PendingListDto;
import com.stylefeng.guns.modular.myplatform.dto.UpdateListDto;
import com.stylefeng.guns.modular.myplatform.service.IMyPlatformService;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.warpper.BaseWarpper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 我的工作台Service
 *
 * @author lgg
 * @Date 2017-11-08 09:58:39
 */
@Service
public class MyPlatformServiceImpl   implements IMyPlatformService {
    @Autowired
    private MyPlatformDao myPlatformDao;
    @Autowired
    private IDeptService deptService;
    /**
     * 我的工作台-待处理项目列表
     */
    @Override
    public Page<PendingListDto> pendingList() {

        Boolean acountContainXXZX = ShiroKit.getUser().getAcountContainXXZX();
        Dept xxzxDept = deptService.getXXZXDept();
        if (xxzxDept == null){//防止空指针
            return null;
        }
        if (acountContainXXZX){ //判断是否含有信息中心角色
            Page<PendingListDto> page = new PageFactory<PendingListDto>().defaultPage();
            List<Map<String,Object>> normalProjects =  myPlatformDao.pendingList(page,xxzxDept.getId());
            page.setRecords((List<PendingListDto>) new BaseWarpper(normalProjects).warp());
            return page;
        }else{
            return null;
        }

    }
    //跟踪项目更新提醒列表
    @Override
    public Page<UpdateListDto> updateList() {
        List<Integer> deptIds = ShiroKit.getUser().getDeptIds();
        Page<UpdateListDto> page = new PageFactory<UpdateListDto>().defaultPage();
        List<Map<String,Object>> normalProjects =  myPlatformDao.updateList(page,deptIds);

        page.setRecords((List<UpdateListDto>) new BaseWarpper(normalProjects).warp());
        return page;
    }
    //项目一周未更新列表
    @Override
    public Page<UpdateListDto> noUpdateList() {
        List<Integer> deptIds = ShiroKit.getUser().getDeptIds();
        Page<UpdateListDto> page = new PageFactory<UpdateListDto>().defaultPage();
        List<Map<String,Object>> normalProjects =  myPlatformDao.noUpdateList(page,deptIds);

        page.setRecords((List<UpdateListDto>) new BaseWarpper(normalProjects).warp());
        return page;
    }
}
