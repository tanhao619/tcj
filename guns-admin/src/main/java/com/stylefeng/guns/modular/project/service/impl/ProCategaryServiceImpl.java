package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProAdviseOpeType;
import com.stylefeng.guns.common.persistence.model.ProCategary;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.ProCategaryDao;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.project.service.IProCategaryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 行业分类Service
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
@Service
public class ProCategaryServiceImpl extends ServiceImpl<BaseMapper<ProCategary>,ProCategary> implements IProCategaryService {

    @Autowired
    private ProCategaryDao proCategaryDao;


    @Override
    public boolean batchMerge(int proId, Map<Object, Object> map) {
        Object cateCount = map.get("cateCount");
        if (cateCount == null || cateCount == "") {
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        ProCategary proCategary = null;
        List<ProCategary> proCategaries = new ArrayList<ProCategary>();
        List<Integer> cateIds = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(cateCount.toString()); i ++ ) {
            proCategary = new ProCategary();
            proCategary.setProId(proId);
            proCategary.setUserId(user.id);
            proCategary.setUserName(user.name);
            proCategary.setName(ParamsUtils.getMap("cateName" + i, map));
            proCategary.setCurrentTime(ParamsUtils.getMap("currentTime", map));
            proCategary.setFolType(Integer.parseInt(ParamsUtils.getMap("from", map)));
            if (StringUtils.isNotBlank(ParamsUtils.getMap("cateId" + i, map))) {
                int cateId = Integer.parseInt(map.get("cateId" + i).toString());
                proCategary.setId(cateId);
                //此处不需要修改
                proCategaryDao.updateProCategary(proCategary);
                cateIds.add(cateId);
                continue;
            }
            proCategaries.add(proCategary);
        }
        if (cateIds.size() > 0 || proCategaries.size() > 0 || Integer.parseInt(cateCount.toString()) == 0) {
            proCategaryDao.updateCurrentTimeByProId(cateIds, proId, ParamsUtils.getMap("currentTime", map),ShiroKit.getUser().id,ShiroKit.getUser().name);
            proCategaryDao.deleteProCategaryByProId(cateIds, proId);
        }
        if (proCategaries.size() > 0) {
            long size  = proCategaryDao.batchInsert(proCategaries);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }
}
