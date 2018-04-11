package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProCategary;
import com.stylefeng.guns.common.persistence.model.ProPackPosition;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.ProPackPositionDao;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.project.service.IProPackPositionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 策划包装定位Service
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
@Service
public class ProPackPositionServiceImpl extends ServiceImpl<BaseMapper<ProPackPosition>,ProPackPosition> implements IProPackPositionService {

    @Autowired
    private ProPackPositionDao proPackPositionDao;

    @Override
    public boolean batchMerge(int proId, Map<Object, Object> map) {
        Object packCount = map.get("packCount");
        if (packCount == null || packCount == "") {
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        ProPackPosition proPackPosition = null;
        List<ProPackPosition> proPackPositions = new ArrayList<ProPackPosition>();
        List<Integer> packIds = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(packCount.toString()); i ++ ) {
            proPackPosition = new ProPackPosition();
            proPackPosition.setProId(proId);
            proPackPosition.setUserId(user.id);
            proPackPosition.setUserName(user.name);
            proPackPosition.setName(ParamsUtils.getMap("packName" + i, map));
            proPackPosition.setCurrentTime(ParamsUtils.getMap("currentTime", map));
            proPackPosition.setFolType(Integer.parseInt(ParamsUtils.getMap("from", map)));
            if (StringUtils.isNotBlank(ParamsUtils.getMap("packId" + i, map))) {
                int packId = Integer.parseInt(map.get("packId" + i).toString());
                proPackPosition.setId(packId);
                //此处不需要修改
                //proPackPositionDao.updateProPackPosition(proPackPosition);
                packIds.add(packId);
                continue;
            }
            proPackPositions.add(proPackPosition);
        }
        if (packIds.size() > 0 || proPackPositions.size() > 0 || Integer.parseInt(packCount.toString()) == 0) {
            proPackPositionDao.updateCurrentTimeByProId(packIds, proId, ParamsUtils.getMap("currentTime", map),ShiroKit.getUser().id,ShiroKit.getUser().name);
            proPackPositionDao.deleteProPackPositionByProId(packIds, proId);
        }
        if (proPackPositions.size() > 0) {
            long size  = proPackPositionDao.batchInsert(proPackPositions);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }
}
