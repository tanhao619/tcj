package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.ProConvention;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.modular.project.dao.ProConventionDao;
import com.stylefeng.guns.modular.project.service.IProConventionService;
import com.stylefeng.guns.modular.system.warpper.BaseWarpper;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.poi.util.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 履约信息Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Service
public class ProConventionServiceImpl extends ServiceImpl<BaseMapper<ProConvention>,ProConvention> implements IProConventionService {
    @Autowired
    private ProConventionDao proConventionDao;

    @Override
    public Page<ProConvention> searchList(Map<String, Object> map) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        Page<ProConvention> page = new PageFactory<ProConvention>().defaultPage();
        List<Map<String, Object>> result = proConventionDao.searchList(page, parasMapE);
        page.setRecords((List<ProConvention>) new BaseWarpper(result).warp());
        return page;
    }

    @Override
    public Integer selectproConventionMaxId(Integer proId) {
        Integer proConventionMaxId=proConventionDao.selectMaxId(proId);
        return proConventionMaxId;
    }
}
