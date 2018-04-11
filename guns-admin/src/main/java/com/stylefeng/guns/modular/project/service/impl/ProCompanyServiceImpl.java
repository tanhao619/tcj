package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProCompany;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.ProCompanyDao;
import com.stylefeng.guns.modular.project.service.IProCompanyService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 投资方公司Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Service
public class ProCompanyServiceImpl extends ServiceImpl<BaseMapper<ProCompany>,ProCompany> implements IProCompanyService {

    @Autowired
    private ProCompanyDao proCompanyDao;

    @Override
    public boolean batchInsertCompanys(int proId, Map map) {
        Object comCount = map.get("comCount");
        if (comCount == null || comCount == "") {
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        ProCompany com = null;
        List<ProCompany> companies = new ArrayList<ProCompany>();
        List<Integer> comIds = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(comCount.toString()); i ++ ) {
            com = new ProCompany();
            com.setProId(proId);
            com.setUserId(user.id);
            com.setUserName(user.name);
            com.setComName(ParamsUtils.getMap("comName" + i,map));
            com.setAddr(ParamsUtils.getMap("comAddr" + i, map));
            com.setAuthor(ParamsUtils.getMap("comAuthor" + i, map));
            com.setTel(ParamsUtils.getMap("comTel" + i, map));
            com.setComType(ParamsUtils.getMap("comType" + i, map));
            com.setContent(ParamsUtils.getMap("comContent" + i, map));
            com.setCurrentTime(ParamsUtils.getMap("currentTime", map));
            com.setFolType(1);
            if (StringUtils.isNotBlank(ParamsUtils.getMap("comId" + i, map))) {
                int comId = Integer.parseInt(map.get("comId" + i).toString());
                com.setId(comId);
                proCompanyDao.updateProCompany(com);
                comIds.add(comId);
                continue;
            }
            companies.add(com);
        }
        if (comIds.size() > 0 || companies.size() > 0 || Integer.parseInt(comCount.toString()) == 0) {
            proCompanyDao.updateCurrentTimeByProId(comIds, proId, map.get("currentTime").toString(),ShiroKit.getUser().id,ShiroKit.getUser().name);
            proCompanyDao.deleteProCompanyByProId(comIds, proId);
        }
        if (companies.size() > 0) {
            long size = proCompanyDao.batchInsert(companies);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }


}
