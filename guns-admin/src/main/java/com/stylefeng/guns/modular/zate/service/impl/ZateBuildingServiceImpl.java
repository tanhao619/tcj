package com.stylefeng.guns.modular.zate.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ExpZatebuilding;
import com.stylefeng.guns.common.persistence.model.ZateBuilding;
import com.stylefeng.guns.modular.util.ParamsUtils;
import com.stylefeng.guns.modular.zate.dao.ZateBuildingDao;
import com.stylefeng.guns.modular.zate.service.IZateBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 楼宇或闲置厂房载体资源Service
 *
 * @author lgg
 * @Date 2017-11-07 23:10:45
 */
@Service
public class ZateBuildingServiceImpl extends ServiceImpl<BaseMapper<ZateBuilding>,ZateBuilding> implements IZateBuildingService {
    @Autowired
    private ZateBuildingDao zateBuildingDao;
    @Override
    public List<ExpZatebuilding> selectByIds(Map<String, Object> parasMap,List<Integer> zids) {
        Map<String, Object> parasMapE = ParamsUtils.encapPara(parasMap);
        List<ExpZatebuilding> expZatebuildings = zateBuildingDao.selectByIds(parasMapE, zids);
        return expZatebuildings;
    }
}
