package com.stylefeng.guns.modular.zate.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.Zate;
import com.stylefeng.guns.common.persistence.model.ZateHistory;
import com.stylefeng.guns.modular.system.warpper.BaseWarpper;
import com.stylefeng.guns.modular.util.ParamsUtils;
import com.stylefeng.guns.modular.zate.dao.ZateDao;
import com.stylefeng.guns.modular.zate.service.IZateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 载体资源管理Service
 *
 * @author lgg
 * @Date 2017-11-07 22:59:39
 */
@Service
public class ZateServiceImpl extends ServiceImpl<BaseMapper<Zate>,Zate> implements IZateService {
    @Autowired
    private ZateDao zateDao;
    @Override
    public Page<Zate> searchList(Map<String, Object> parasMap) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(parasMap);
        Page<Zate> page = new PageFactory<Zate>().defaultPage();
        List<Map<String, Object>> result = new ArrayList<>();
        if ("ordId".equals(page.getOrderByField())){
            page.setOrderByField("zid");
        }
        if ("".equals(parasMapE.get("sort")) || parasMapE.get("sort") == null){
             result = zateDao.searchList(page,parasMapE,true);
        }else {
            result = zateDao.searchList(page,parasMapE,false);
        }
        page.setRecords((List<Zate>) new BaseWarpper(result).warp());
        return page;
    }

    @Override
    public List<Zate>  searchExportList(Map<String, Object> parasMap) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(parasMap);
        List<Zate> zates = zateDao.searchExportList(parasMapE);

        return zates;
    }


    @Override
    public List<ZateHistory> exportExcel() {
        return null;
    }
}
