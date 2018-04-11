package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProArea;
import com.stylefeng.guns.common.persistence.model.ProCompany;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.ProAreaDao;
import com.stylefeng.guns.modular.project.service.IProAreaService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目地址及用地Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Service
public class ProAreaServiceImpl extends ServiceImpl<BaseMapper<ProArea>,ProArea> implements IProAreaService {

    @Autowired
    private ProAreaDao proAreaDao;

    @Override
    public boolean batchMerge(int proId, Map<Object, Object> map) {
        Object areaCount = map.get("areaCount");
        if (areaCount == null || areaCount == "") {
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        ProArea area = null;
        List<ProArea> areas = new ArrayList<ProArea>();
        List<Integer> areaIds = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(areaCount.toString()); i ++ ) {
            area = new ProArea();
            area.setProId(proId);
            area.setUserId(user.id);
            area.setUserName(user.name);
            area.setAddr(ParamsUtils.getMap("areaAddr" + i, map));
            area.setUseType(ParamsUtils.getMap("areaUseType" + i, map));
            area.setUseArea(ParamsUtils.getMap("areaUseArea" + i, map));
            area.setCurrentTime(ParamsUtils.getMap("currentTime", map));
            area.setFolType(Integer.parseInt(ParamsUtils.getMap("from", map)));
            if (StringUtils.isNotBlank(ParamsUtils.getMap("areaId" + i, map))) {
                int areaId = Integer.parseInt(map.get("areaId" + i).toString());
                area.setId(areaId);
                proAreaDao.updateProArea(area);
                areaIds.add(areaId);
                continue;
            }
            areas.add(area);
        }
        if (areaIds.size() > 0 || areas.size() > 0 || Integer.parseInt(areaCount.toString()) == 0) {
            proAreaDao.updateCurrentTimeByProId(areaIds, proId, ParamsUtils.getMap("currentTime", map),ShiroKit.getUser().id,ShiroKit.getUser().name);
            proAreaDao.deleteProAreaByProId(areaIds, proId);
        }
        if (areas.size() > 0) {
            long size  = proAreaDao.batchInsert(areas);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }
}
