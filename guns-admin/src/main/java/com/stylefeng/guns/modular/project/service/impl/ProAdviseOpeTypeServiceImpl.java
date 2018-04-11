package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProAdviseOpeType;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.project.dao.ProAdviseOpeTypeDao;
import com.stylefeng.guns.modular.project.service.IProAdviseOpeTypeService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目实施类型管理Service
 *
 * @author monkey1
 * @Date 2017-11-16 21:06:50
 */
@Service
public class ProAdviseOpeTypeServiceImpl extends ServiceImpl<BaseMapper<ProAdviseOpeType>,ProAdviseOpeType> implements IProAdviseOpeTypeService {

    @Autowired
    private ProAdviseOpeTypeDao proAdviseOpeTypeDao;

    @Override
    public boolean batchMerge(int proId, Map<Object, Object> map) {
        Object adviseCount = map.get("adviseCount");
        if (adviseCount == null || adviseCount == "") {
            return false;
        }
        ShiroUser user = ShiroKit.getUser();
        ProAdviseOpeType proAdviseOpeType = null;
        List<ProAdviseOpeType> proAdviseOpeTypes = new ArrayList<ProAdviseOpeType>();
        List<Integer> adviseIds = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(adviseCount.toString()); i ++ ) {
            proAdviseOpeType = new ProAdviseOpeType();
            proAdviseOpeType.setProId(proId);
            proAdviseOpeType.setUserId(user.id);
            proAdviseOpeType.setUserName(user.name);
            proAdviseOpeType.setName(ParamsUtils.getMap("adviseName" + i, map));
            proAdviseOpeType.setCurrentTime(ParamsUtils.getMap("currentTime", map));
            proAdviseOpeType.setFolType(Integer.parseInt(ParamsUtils.getMap("from", map)));
            if (StringUtils.isNotBlank(ParamsUtils.getMap("adviseId" + i, map))) {
                int adviseId = Integer.parseInt(map.get("adviseId" + i).toString());
                proAdviseOpeType.setId(adviseId);
                //此处不需要修改
                //proAdviseOpeTypeDao.updateProAdviseOpeType(proAdviseOpeType);
                adviseIds.add(adviseId);
                continue;
            }
            proAdviseOpeTypes.add(proAdviseOpeType);
        }
        if (adviseIds.size() > 0 || proAdviseOpeTypes.size() > 0 || Integer.parseInt(adviseCount.toString()) == 0) {
            proAdviseOpeTypeDao.updateCurrentTimeByProId(adviseIds, proId, ParamsUtils.getMap("currentTime", map),ShiroKit.getUser().id,ShiroKit.getUser().name);
            proAdviseOpeTypeDao.deleteProAdviseOpeTypeByProId(adviseIds, proId, Integer.parseInt(ParamsUtils.getMap("from", map)));
        }
        if (proAdviseOpeTypes.size() > 0) {
            long size  = proAdviseOpeTypeDao.batchInsert(proAdviseOpeTypes);
            if (size > 0) {
                return true;
            }
        }
        return false;
    }
}
