package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProIndustryType;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.ProIndustryTypeDao;
import com.stylefeng.guns.modular.project.service.IProIndustryTypeService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产业分类表Service
 *
 * @author monkey
 * @Date 2018-01-04 10:41:23
 */
@Service
public class ProIndustryTypeServiceImpl extends ServiceImpl<BaseMapper<ProIndustryType>,ProIndustryType> implements IProIndustryTypeService {
    @Autowired
    private ProIndustryTypeDao proIndustryTypeDao;

    @Override
    public boolean batchMerge(int proId, Map<Object, Object> map) {
        Object cyCount = map.get("cyCount");
        if (cyCount == null || cyCount == "") {
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        ProIndustryType proIndustryType = null;
        List<ProIndustryType> proIndustryTypes = new ArrayList<ProIndustryType>();
        List<Integer> proIndustryIds = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(cyCount.toString()); i ++ ) {
            proIndustryType = new ProIndustryType();
            proIndustryType.setProId(proId);
            proIndustryType.setUserId(user.id);
            proIndustryType.setUserName(user.name);
            proIndustryType.setName(ParamsUtils.getMap("cyName" + i, map));
            proIndustryType.setCurrentTime(ParamsUtils.getMap("currentTime", map));
            proIndustryType.setFolType(Integer.parseInt(ParamsUtils.getMap("from", map)));
            if (StringUtils.isNotBlank(ParamsUtils.getMap("cyId" + i, map))) {
                int comId = Integer.parseInt(map.get("cyId" + i).toString());
                proIndustryType.setId(comId);
                //此处不需要修改
                //proContractTypeDao.updateProContractType(proContractType);
                proIndustryIds.add(comId);
                continue;
            }
            proIndustryTypes.add(proIndustryType);
        }
        if (proIndustryIds.size() > 0 || proIndustryTypes.size() > 0 || Integer.parseInt(cyCount.toString()) == 0) {
            proIndustryTypeDao.updateCurrentTimeByProId(proIndustryIds, proId, ParamsUtils.getMap("currentTime", map),ShiroKit.getUser().id,ShiroKit.getUser().name,Integer.parseInt(ParamsUtils.getMap("from", map)));
            proIndustryTypeDao.deleteProIndustryTypeByProId(proIndustryIds, proId, Integer.parseInt(ParamsUtils.getMap("from", map)));
        }
        if (proIndustryTypes.size() > 0) {
            long size  = proIndustryTypeDao.batchInsert(proIndustryTypes);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Map> selectByProId(Map<Object, Object> map) {
        return proIndustryTypeDao.queryIndustryTypeByProId(map);
    }

    @Override
    public void batchInsert(List<ProIndustryType> proIndustryTypes) {
        proIndustryTypeDao.batchInsert(proIndustryTypes);
    }


}
