package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.ProWorkProcess;
import com.stylefeng.guns.modular.project.dao.ProWorkProcessDao;
import com.stylefeng.guns.modular.system.warpper.BaseWarpper;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.project.service.IProWorkProcessService;

import java.util.List;
import java.util.Map;

/**
 * 工作进度Service
 *
 * @author monkey
 * @Date 2017-11-30 11:20:24
 */
@Service
public class ProWorkProcessServiceImpl extends ServiceImpl<BaseMapper<ProWorkProcess>,ProWorkProcess> implements IProWorkProcessService {
    @Autowired
    private ProWorkProcessDao proWorkProcessDao;

    @Override
    public Integer selectproWorkProcessMaxId(Integer proId) {
        Integer maxId=proWorkProcessDao.seleMaxId(proId);
        return maxId;
    }

    @Override
    public Page<ProWorkProcess> searchList(Map<String, Object> map) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        Page<ProWorkProcess> page = new PageFactory<ProWorkProcess>().defaultPage();
        List<Map<String, Object>> result = proWorkProcessDao.searchList(page, parasMapE);
        page.setRecords((List<ProWorkProcess>) new BaseWarpper(result).warp());
        return page;
    }

    @Override
    public boolean batchMerge(int proId, Map<Object, Object> map) {
        Object isMerge = map.get("isMerge");
        if (isMerge == null) {
           proWorkProcessDao.insert(proId, map);
            return true;
        } else {
            return false;
        }

    }
}
