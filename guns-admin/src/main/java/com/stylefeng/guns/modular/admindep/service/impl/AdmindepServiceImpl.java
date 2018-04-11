package com.stylefeng.guns.modular.admindep.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.GovaffDatamanAdmindep;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.modular.admindep.dao.AdmindepDao;
import com.stylefeng.guns.modular.admindep.service.IAdmindepService;
import com.stylefeng.guns.modular.system.warpper.BaseWarpper;
import com.stylefeng.guns.modular.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 部门信息Service
 *
 * @author lgg
 * @Date 2017-10-26 15:23:14
 */
@Service
public class AdmindepServiceImpl extends ServiceImpl<BaseMapper<GovaffDatamanAdmindep>,GovaffDatamanAdmindep> implements IAdmindepService {

    @Autowired
    private AdmindepDao admindepDao;

    @Override
    public Page<GovaffDatamanAdmindep> searchList(GovaffDatamanAdmindep govaffDatamanAdmindep,String searchTimes,String searchLikes){

        Map<String, Map<String, Object>> parmsMap = SearchUtil.encapSearchConditions(govaffDatamanAdmindep, searchTimes, searchLikes);
        Page<GovaffDatamanAdmindep> page = new PageFactory<GovaffDatamanAdmindep>().defaultPage();
        List<Map<String, Object>> result = admindepDao.searchList(page,parmsMap);
        page.setRecords((List<GovaffDatamanAdmindep>) new BaseWarpper(result).warp());
        return page;

    }

}
