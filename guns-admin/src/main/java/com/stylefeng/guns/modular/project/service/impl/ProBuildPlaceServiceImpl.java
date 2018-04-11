package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProBuildPlace;
import com.stylefeng.guns.common.persistence.model.ProCategary;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.ProBuildPlaceDao;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.project.service.IProBuildPlaceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 拟建设地点Service
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
@Service
public class ProBuildPlaceServiceImpl extends ServiceImpl<BaseMapper<ProBuildPlace>,ProBuildPlace> implements IProBuildPlaceService {
    @Autowired
    private ProBuildPlaceDao proBuildPlaceDao;

    @Override
    public boolean batchMerge(int proId, Map<Object, Object> map) {
        Object bpCount = map.get("bpCount");
        if (bpCount == null || bpCount == "") {
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        ProBuildPlace proBuildPlace = null;
        List<ProBuildPlace> proBuildPlaces = new ArrayList<ProBuildPlace>();
        List<Integer> buildPlaceIds = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(bpCount.toString()); i ++ ) {
            proBuildPlace = new ProBuildPlace();
            proBuildPlace.setProId(proId);
            proBuildPlace.setUserId(user.id);
            proBuildPlace.setUserName(user.name);
            proBuildPlace.setName(ParamsUtils.getMap("buildPlaceName" + i, map));
            proBuildPlace.setCurrentTime(ParamsUtils.getMap("currentTime", map));
            proBuildPlace.setFolType(Integer.parseInt(ParamsUtils.getMap("from", map)));
            if (StringUtils.isNotBlank(ParamsUtils.getMap("buildPlaceId" + i, map))) {
                int buildPlaceId = Integer.parseInt(map.get("buildPlaceId" + i).toString());
                proBuildPlace.setId(buildPlaceId);
                //此处不需要修改
                proBuildPlaceDao.updateProBuildPlace(proBuildPlace);
                buildPlaceIds.add(buildPlaceId);
                continue;
            }
            proBuildPlaces.add(proBuildPlace);
        }
        if (buildPlaceIds.size() > 0 || proBuildPlaces.size() > 0 || Integer.parseInt(bpCount.toString()) == 0) {
            proBuildPlaceDao.updateCurrentTimeByProId(buildPlaceIds, proId, ParamsUtils.getMap("currentTime", map),ShiroKit.getUser().id,ShiroKit.getUser().name);
            proBuildPlaceDao.deleteProBuildPlaceByProId(buildPlaceIds, proId);
        }
        if (proBuildPlaces.size() > 0) {
            long size  = proBuildPlaceDao.batchInsert(proBuildPlaces);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }
}
