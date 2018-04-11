package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProCompany;
import com.stylefeng.guns.common.persistence.model.UnitLiable;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.UnitLiableDao;
import com.stylefeng.guns.modular.project.service.IUnitLiableService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 责任单位Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Service
public class UnitLiableServiceImpl extends ServiceImpl<BaseMapper<UnitLiable>,UnitLiable> implements IUnitLiableService {

    @Autowired
    private UnitLiableDao unitLiableDao;

    @Override
    public boolean batchMerge(int proId, Map<Object, Object> map) {
        Object unitCount = map.get("unitCount");
        if (unitCount == null || unitCount == "") {
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        UnitLiable unitLiable = null;
        List<UnitLiable> unitLiables = new ArrayList<UnitLiable>();
        List<Integer> unitLiableIds = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(unitCount.toString()); i ++ ) {
            unitLiable = new UnitLiable();
            unitLiable.setProId(proId);
            unitLiable.setUserId(user.id);
            unitLiable.setUserName(user.name);
            unitLiable.setLiable(ParamsUtils.getMap("unitLiable" + i, map));
            unitLiable.setName(ParamsUtils.getMap("unitName" + i, map));
            unitLiable.setTel(ParamsUtils.getMap("unitTel" + i, map));
            unitLiable.setCurrentTime(ParamsUtils.getMap("currentTime", map));
            unitLiable.setFolType(Integer.parseInt(ParamsUtils.getMap("from", map)));
            if (StringUtils.isNotBlank(ParamsUtils.getMap("unitId" + i, map))) {
                int comId = Integer.parseInt(map.get("unitId" + i).toString());
                unitLiable.setId(comId);
                unitLiableDao.updateUnitLiable(unitLiable);
                unitLiableIds.add(comId);
                continue;
            }
            unitLiables.add(unitLiable);
        }
        if (unitLiableIds.size() > 0 || unitLiables.size() > 0 || Integer.parseInt(unitCount.toString()) == 0) {
            unitLiableDao.updateCurrentTimeByProId(unitLiableIds, proId, ParamsUtils.getMap("currentTime", map),ShiroKit.getUser().id,ShiroKit.getUser().name);
            unitLiableDao.deleteUnitLiableByProId(unitLiableIds, proId, Integer.parseInt(ParamsUtils.getMap("from", map)));
        }
        if (unitLiables.size() > 0) {
            long size  = unitLiableDao.batchInsert(unitLiables);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }
}
