package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProCompany;
import com.stylefeng.guns.common.persistence.model.ProContractType;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.ProContractTypeDao;
import com.stylefeng.guns.modular.project.service.IProContractTypeService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目合同类型管理Service
 *
 * @author monkey1
 * @Date 2017-11-16 21:07:25
 */
@Service
public class ProContractTypeServiceImpl extends ServiceImpl<BaseMapper<ProContractType>,ProContractType> implements IProContractTypeService {

    @Autowired
    private ProContractTypeDao proContractTypeDao;

    @Override
    public boolean batchMerge(int proId, Map<Object, Object> map) {
        Object htCount = map.get("htCount");
        if (htCount == null || htCount == "") {
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        ProContractType proContractType = null;
        List<ProContractType> proContractTypes = new ArrayList<ProContractType>();
        List<Integer> proContractTypeIds = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(htCount.toString()); i ++ ) {
            proContractType = new ProContractType();
            proContractType.setProId(proId);
            proContractType.setUserId(user.id);
            proContractType.setUserName(user.name);
            proContractType.setName(ParamsUtils.getMap("htName" + i, map));
            proContractType.setCurrentTime(ParamsUtils.getMap("currentTime", map));
            proContractType.setFolType(Integer.parseInt(ParamsUtils.getMap("from", map)));
            if (StringUtils.isNotBlank(ParamsUtils.getMap("htId" + i, map))) {
                int comId = Integer.parseInt(map.get("htId" + i).toString());
                proContractType.setId(comId);
                //此处不需要修改
                //proContractTypeDao.updateProContractType(proContractType);
                proContractTypeIds.add(comId);
                continue;
            }
            proContractTypes.add(proContractType);
        }
        if (proContractTypeIds.size() > 0 || proContractTypes.size() > 0 || Integer.parseInt(htCount.toString()) == 0) {
            proContractTypeDao.updateCurrentTimeByProId(proContractTypeIds, proId, ParamsUtils.getMap("currentTime", map),ShiroKit.getUser().id,ShiroKit.getUser().name);
            proContractTypeDao.deleteProContractTypeByProId(proContractTypeIds, proId);
        }
        if (proContractTypes.size() > 0) {
            long size  = proContractTypeDao.batchInsert(proContractTypes);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }
}
